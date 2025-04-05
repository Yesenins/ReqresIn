package Reqres_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Color {
    @Expose
    Color data;

    @Expose
    String name;

    @Expose
    String color;

    @Expose
    int id;

    @Expose
    int year;

    @Expose
    @SerializedName("pantone_value")
    String pantoneValue;
}
