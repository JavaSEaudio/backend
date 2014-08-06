package rest.service;


import BusinessLogic.Sessions;
import DAO.util.Factory;
import DTO.UserDTO;
import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.net.SocketException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Path("get")
public class AudioGet {
    @GET
    @Path("/audio")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getAudio(@CookieParam("name") String uid,
                             @QueryParam(value = "id") int id,
                             @QueryParam(value = "ext") String extension
    )throws IOException, WebApplicationException, SocketException {
        String path = "C://upload//audio//"+id+".mp3";
        int userid = Sessions.uid(uid);
        if(userid == -1) {
            Response.ok().build();
        }
        boolean bool = false;
        for(int i : new UserDTO(Factory.getInstance().getUserDAO().getById(userid)).getBuyListArray()){
            if(i == id) {
                bool = true;
                break;
            }
        }
        if(!bool){
            Response.ok().build();
        }
        File file;
        try {
            if(extension != null &&extension.equals("mp3") == false){
                path = convertFile(path, extension);
            }
            file = new File(path);
        } catch (Exception e) {
            file = new File("");
            Response.ok().build();
        }
        if (!file.exists() || file.isDirectory()) {
            Response.ok().build();
        }
        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" )
                .build();
    }


    @GET
    @Path("/image")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getImage(@QueryParam(value = "id") int id) {
        String path = "C://upload//image//"+id+".jpg";
        File file;
        try {
            file = new File(path);
        } catch (Exception e) {
            file = new File("C://upload//image//0.jpg");
        }
        if (!file.exists() || file.isDirectory()) {
            file = new File("C://upload//image//0.jpg");
        }
        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" )
                .build();
    }
    @GET
    @Path("/avatar")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getAvatar(@QueryParam(value = "id") int id)throws IOException, WebApplicationException, SocketException {
        String path = "C://upload//user//"+id+".jpg";
        File file;
        try {
            file = new File(path);
        } catch (Exception e) {
            file = new File("C://upload//user//0.jpg");
        }
        if (!file.exists() || file.isDirectory()) {
            file = new File("C://upload//user//0.jpg");
        }
        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + file.getName()+ "\"" ) //optional
                .build();
    }

    @GET
    @Path("/private")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getPrivate(@QueryParam(value = "id") int id,
                               @CookieParam("name") String uid) throws IOException, WebApplicationException, SocketException {
        int userid = Sessions.uid(uid);
        if(userid == -1){
            return Response.status(400).entity("login pls").build();
        }
        if(
            Factory.getInstance().getPrivateDAO().getById(id).getUserid() != userid
                ) {
            return Response.status(400).entity("not access").build();
        }


        String path = "C://upload//private//"+id+".mp3";
        File file;
        try {
            file = new File(path);
        } catch (Exception e) {
            file = new File("");
        }
        if (!file.exists() || file.isDirectory()) {
            file = new File("");
        }
        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + file.getName()+ "\"" ) //optional
                .build();
    }
    @GET
    @Path("/privateImage")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getPrivateImage(@QueryParam(value = "id") int id,
                                    @CookieParam("name") String uid) {
        int userid = Sessions.uid(uid);
        if(userid == -1){
            return Response.status(400).entity("login pls").build();
        }
        String path = "C://upload//privateImage//"+id+".jpg";
        File file;
        try {
            file = new File(path);
        } catch (Exception e) {
            file = new File("C://upload//image//0.jpg");
        }
        if (!file.exists() || file.isDirectory()) {
            file = new File("C://upload//image//0.jpg");
        }
        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" )
                .build();
    }

    public static String convertFile(String inFilePath, String extension){
        File source = new File(inFilePath);
        String nameOfFile = getExpansion(source.getName());
        String outFilePath = inFilePath.replaceAll(nameOfFile,"")+extension;

        File target = new File(outFilePath);
        if( target.exists()== false) {

            AudioAttributes audio = new AudioAttributes();
            if (extension.equals("flac")) {
                audio.setCodec("flac");
            }
            if (extension.equals("wav")) {
                audio.setCodec("wmav2");
            }
            if (extension.equals("ogg")) {
                audio.setCodec("vorbis");
            }
            audio.setBitRate(new Integer(128000));
            audio.setChannels(new Integer(2));
            audio.setSamplingRate(new Integer(44100));

            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setFormat(extension);
            attrs.setAudioAttributes(audio);
            Encoder encoder = new Encoder();
            try {
                encoder.encode(source, target, attrs);
            } catch (EncoderException e) {
                e.printStackTrace();
            }
        }
        return outFilePath;
    }

    public static String getExpansion (String fileName){
        String expansion;
        String regexpAudio1 = "([^\\s]+(\\.(?i)(mp3|wav|og(?:g|a)|flac|midi?|rm|aac|wma|mka|ape))$)";
        Pattern pattern = Pattern.compile(regexpAudio1);
        Matcher matcher = pattern.matcher(fileName);

        if(matcher.matches()){
            String [] temp =  fileName.split("\\.");
            expansion = temp[temp.length-1];
        }else {
            expansion = "";
        }
        return expansion;
    }
}
