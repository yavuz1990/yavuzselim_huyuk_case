package apiTest.endpoints;

public class Routes {

    public static String base_url="https://petstore.swagger.io/v2";

    // User Module

    public static String post_url=base_url+"/user";
    public static String get_url=base_url+"/user/{username}";
    public static String put_url=base_url+"/user/{username}";
    public static String delete_url=base_url+"/user/{username}";


    // Store Module



    // Pet Module
    public static String find_by_status= base_url+"/pet/findByStatus";
    public static String add_a_new_pet= base_url+"/pet";
    public static String update_pet= base_url+"/pet";
    public static String find_pet_id= base_url+"/pet/{petId}";
    public static String update_pet_with_form_data= base_url+"/pet/{petId}";
    public static String upload_image= base_url+"/pet/{petId}/uploadImage";
    public static String deletePet= base_url+"/pet/{petId}";



}
