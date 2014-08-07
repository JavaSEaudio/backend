package rest.file;

import BusinessLogic.FileOperation;
import BusinessLogic.Sessions;
import DAO.PrivateDAO;
import DAO.SessionDAO;
import DAO.util.Factory;
import DTO.GetListDTO;
import DTO.PrivateDTO;
import Entity.AudioEntity;
import Entity.PrivateEntity;
import org.apache.log4j.Logger;
import util.CopyFiles;
import util.Cut;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("/private")
public class PrivateFile {
    private final static Logger log =  Logger.getLogger("com.audiostorage.report");
    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAudios(@QueryParam("count") int count,
                              @QueryParam("page") int page,
                              @CookieParam("name") String uid
    ) {
        int userid = Sessions.uid(uid);
        if(count > 100) count = 100;
        List<PrivateEntity> audio = new ArrayList<PrivateEntity>();
        List<PrivateDTO> result;

        try {
            PrivateDAO aDAO = Factory.getInstance().getPrivateDAO();
            audio.addAll(aDAO.getSomePrivates((count * (page - 1)), count, userid));
        } catch (Exception e) {
            log.info("Private Get: exception");
        }
        result = GetListDTO.getListPrivateDTO(audio);
        return Response.ok(new GenericEntity<ArrayList<PrivateDTO>>((ArrayList<PrivateDTO>)result){}).build();
    }
    @GET
    @Path("/audio/getbyid")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getbyid(@CookieParam(value = "name") String uid,
                            @QueryParam("id") int idFile
    ) {
        int userid = Sessions.uid(uid);
        if (userid == -1) {
            log.info("Delete File: not logged in");
            return Response.status(400).entity("You can't edit file! Please sign in!!!").build();
        }
        PrivateEntity audio = null;
        try {
            PrivateDAO aDAO = Factory.getInstance().getPrivateDAO();
            audio = aDAO.getById(idFile);
        } catch(Exception e) {
            log.info("Private ByID: exception");
        }
        PrivateDTO audioDTO = new PrivateDTO(audio);
        return Response.ok().entity(audioDTO).build();
    }


    @GET
    @Path("/file/delete")
    public Response deleteFile(@CookieParam(value = "name") String uid,
                               @QueryParam("id") int idFile
    ) {
        int userid = Sessions.uid(uid);
        if (userid == -1) {
            log.info("Delete File: not logged in");
            return Response.status(400).entity("You can't edit file! Please sign in!!!").build();
        }
        PrivateDAO privateDAO = Factory.getInstance().getPrivateDAO();
        PrivateEntity privateEntity = privateDAO.getById(idFile);
        if (userid != privateEntity.getUserid()) {
            log.info("Delete File: not access");
            return Response.status(400).entity("not access").build();
        }
        File file = new File("C://upload//private//"+privateEntity.getId()+".mp3");
        if (file.delete()) {
            new File("C://upload//privateImage//"+privateEntity.getId()+".jpg").delete();
            new File("C://upload//private//"+privateEntity.getId()+".tmp").delete();
            new File("C://upload//private//"+privateEntity.getId()+".ogg").delete();
            new File("C://upload//private//"+privateEntity.getId()+".wav").delete();
            privateDAO.delete(privateEntity);
            log.info("Delete File: " + file.getName() + " deleted");
            return Response.status(200).build();
        }
        log.info("Delete File: " + file.getName() + " not removed");
        return Response.status(400).entity("not removed").build();
    }

    @POST
    @Path("/edit")
    public Response editFile(@CookieParam(value = "name") String uid,
                             @QueryParam("idfile") int id,
                             @FormParam("title") String name,
                             @FormParam("album") String album,
                             @FormParam("artist") String artist,
                             @FormParam("access") String access
    ) {
        int userid = Sessions.uid(uid);
        if (userid == -1) {
            log.info("Edit File: not logged in");
            return Response.status(200).entity("You can't edit file! Please sign in!!!").build();
        }

        PrivateDAO privateDAO = Factory.getInstance().getPrivateDAO();
        PrivateEntity privateEntity = privateDAO.getById(id);
        if (userid != privateEntity.getUserid()) {
            log.info("Edit File: not access");
            return Response.status(200).entity("You can't edit this file!!").build();
        }

        FileOperation fileEdit = new FileOperation("C://upload//private//"+privateEntity.getId()+".mp3");
        if ((name != null) || (!name.equals(""))) {
            try {
                fileEdit.setName(name);

            } catch (Exception e) {}
            privateEntity.setName(name);
        }
        if ((album != null) || (!album.equals(""))) {
            try {
                fileEdit.setAlbum(album);

            } catch (Exception e) {}
            privateEntity.setAlbum(album);
        }
        if ((artist != null) || (!artist.equals(""))) {
            try {
                fileEdit.setArtist(artist);

            } catch (Exception e) {}
            privateEntity.setArtist(artist);
        }
        if(access == null){
            AudioEntity audioEntity = new AudioEntity(privateEntity);
            Factory.getInstance().getAudioDAO().add(audioEntity);
            File source = new File("C://upload//private//"+privateEntity.getId()+".mp3");
            File destination = new File("C://upload//audio//"+audioEntity.getId()+".mp3");
            try {
                CopyFiles.copyFileUsingStream(source, destination);

            } catch (IOException e) {
                e.printStackTrace();
            }
            source = new File("C://upload//private//"+privateEntity.getId()+".wav");
            destination = new File("C://upload//audio//"+audioEntity.getId()+".wav");
            try {
                CopyFiles.copyFileUsingStream(source, destination);

            } catch (Exception e) {}
            source = new File("C://upload//private//"+privateEntity.getId()+".ogg");
            destination = new File("C://upload//audio//"+audioEntity.getId()+".ogg");
            try {
                CopyFiles.copyFileUsingStream(source, destination);

            } catch (Exception e) {}
//            Cut.file("C://upload//audio//"+audioEntity.getId()+".mp3");
            source.delete();
            source = new File("C://upload//privateImage//"+privateEntity.getId()+".jpg");
            destination = new File("C://upload//image//"+audioEntity.getId()+".jpg");
            try {
                CopyFiles.copyFileUsingStream(source, destination);
            } catch (Exception e) {
                e.printStackTrace();
            }
            source.delete();
            privateDAO.delete(privateEntity);
            log.info("Edit File: private success");
            return Response.status(200).entity("private success").build();
        } else if(access.equals("private")){
            Factory.getInstance().getPrivateDAO().change(privateEntity);
            log.info("Edit File: success");
            return Response.status(200).entity("suc").build();
        }
        return Response.status(404).build();
    }
    final int chunk_size = 1024 * 1024; // 1MB chunks
    private File audio = null;




        @GET
        @Path("/listen")
        @Produces("audio/mp3")
        public Response streamAudio(@QueryParam("id") int id,
                                    @CookieParam("name") String uid,
                                    @HeaderParam("Range") String range) throws Exception {
            int userid = Sessions.uid(uid);
            if (userid == -1) {
                log.info("Edit File: not logged in");
                return Response.status(400).entity("You can't listen file! Please sign in!!!").build();
            }
            PrivateEntity privateEntity =Factory.getInstance().getPrivateDAO().getById(id);
            if(privateEntity.getUserid() != userid){
                return Response.status(400).entity("you can not listen to someone else's file!!!").build();
            }
            audio = new File("C://upload//private//"+id+".mp3");
            return buildStream(audio, range);
        }

        private Response buildStream(final File asset, final String range) throws Exception {
            // range not requested : Firefox, Opera, IE do not send range headers
            if (range == null) {
                StreamingOutput streamer = new StreamingOutput() {
                    @Override
                    public void write(final OutputStream output) throws IOException, WebApplicationException {

                        final FileChannel inputChannel = new FileInputStream(asset).getChannel();
                        final WritableByteChannel outputChannel = Channels.newChannel(output);
                        try {
                            inputChannel.transferTo(0, inputChannel.size(), outputChannel);
                        } finally {
                            // closing the channels
                            inputChannel.close();
                            outputChannel.close();
                        }
                    }
                };
                return Response.ok(streamer).status(200).header(HttpHeaders.CONTENT_LENGTH, asset.length()).build();
            }

            String[] ranges = range.split("=")[1].split("-");
            final int from = Integer.parseInt(ranges[0]);
            /**
             * Chunk media if the range upper bound is unspecified. Chrome sends "bytes=0-"
             */
            int to = chunk_size + from;
            if (to >= asset.length()) {
                to = (int) (asset.length() - 1);
            }
            if (ranges.length == 2) {
                to = Integer.parseInt(ranges[1]);
            }

            final String responseRange = String.format("bytes %d-%d/%d", from, to, asset.length());
            final RandomAccessFile raf = new RandomAccessFile(asset, "r");
            raf.seek(from);

            final int len = to - from + 1;
            final MediaStreamer streamer = new MediaStreamer(len, raf);
            Response.ResponseBuilder res = Response.ok(streamer).status(206)
                    .header("Accept-Ranges", "bytes")
                    .header("Content-Range", responseRange)
                    .header(HttpHeaders.CONTENT_LENGTH, streamer.getLenth())
                    .header(HttpHeaders.LAST_MODIFIED, new Date(asset.lastModified()));
            return res.build();
        }

        public class MediaStreamer implements StreamingOutput {

            private int length;
            private RandomAccessFile raf;
            final byte[] buf = new byte[4096];

            public MediaStreamer(int length, RandomAccessFile raf) {
                this.length = length;
                this.raf = raf;
            }

            @Override
            public void write(OutputStream outputStream) throws IOException, WebApplicationException {
                try {
                    while( length != 0) {
                        int read = raf.read(buf, 0, buf.length > length ? length : buf.length);
                        outputStream.write(buf, 0, read);
                        length -= read;
                    }
                } finally {
                    raf.close();
                }
            }

            public int getLenth() {
                return length;
            }
        }



}
