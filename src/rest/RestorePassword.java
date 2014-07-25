package rest;



        import javax.ws.rs.*;
        import javax.ws.rs.core.*;
        import BusinessLogic.Key;
        import Entity.UserEntity;
        import DAO.UserDAO;
        import util.EmailSender;
        import util.Factory;

@Path("/pwd")
public class RestorePassword {

    @GET
    @Consumes("text/html")
    @Produces("text/html")
    // принимаем и сохраняем полученный имейл
    public Response resetPassword(@FormParam("email") String email) {
        // генерируем новый пароль
        Key newPasswordGenerator = new Key();
        String newPassword = newPasswordGenerator.getValue();
        System.out.println(newPassword);
        // ищем в базе пользователя по имейлу
        UserDAO uDAO = Factory.getInstance().getUserDAO();
        UserEntity user = uDAO.getByEmail(email);
        System.out.println(user.getPassword());
        uDAO.changePassword(user, newPassword);
        System.out.println(uDAO.getByEmail(email).getPassword());
        // записываем нвоый пароль в базу
        EmailSender.sendPassword(email, newPassword);
        // высылаем нвоый пароль на указанный имейл


        System.out.println(email);
        return Response.status(200).entity(email).build();

    }


}