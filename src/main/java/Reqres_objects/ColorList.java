package Reqres_objects;

import com.google.gson.annotations.Expose;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
public class ColorList {
    @Expose
    ArrayList<Color> data;
}
