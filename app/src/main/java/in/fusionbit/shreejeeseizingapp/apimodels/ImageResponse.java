package in.fusionbit.shreejeeseizingapp.apimodels;

public class ImageResponse {


    /**
     * image_name : shreejee_seizing_eee840599c52e5b0883259d810c2640f.jpg
     */

    private String image_name;

    public String getImage_name() {
        if(image_name==null){
            return "Image not uploaded";
        }
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }
}
