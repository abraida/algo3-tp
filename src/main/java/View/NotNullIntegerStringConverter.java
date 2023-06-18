package View;

import javafx.util.converter.IntegerStringConverter;

public class NotNullIntegerStringConverter extends IntegerStringConverter {
    @Override
    public Integer fromString(String var1) {
        if (var1.equals("")) {
            return 0;
        }
        return super.fromString(var1);
    }

    @Override
    public String toString(Integer var1) {
        return var1 == null ? "0" : Integer.toString(var1);
    }

}