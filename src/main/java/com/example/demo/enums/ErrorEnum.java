package com.example.demo.enums;


public enum ErrorEnum {
    NULL_FT(1101, "First test null"),

    // Error code table : 100*
    Table_exist(1001, "Table exist in database"),
    Table_not_exist(1002, "Table not exist in database"),
    Table_not_found(1003, "Table not Found"),
    Table_key_expired(1004, "table's key expired or wrong"),
    Table_being_served(1005, "Tables are being serving"),
    // error code Food : 110*
    FOOD_ALREADY_EXISTS(1101, "Name's Food already exists"),
    FOOD_NOT_EXISTS(1102, "Food not exists"),
    Name_food_not_blank(1103, "Name food not blank "),
    Price_food_not_negative(1104, "Price food not negative"),
    Price_food_not_null(1105, "Pirce food not null"),
    Is_Selling_not_null(1106, "Is Selling not null"),
    Is_Deleted_not_null(1107, "Is Deleted not null"),
    Invalid_is_Selling(1108, "Invalid isSelling"),
    Invalid_id_Category(1109, "Invalid Id Category"),
    SOME_FOOD_NOT_EXISTS(1110, "Some food not exists"),
    ID_FOOD_NOT_NULL(1111, "ID food not null")
    // erroe code qr : 120*
    , QR_exist(1201, "Qr exist in database"),

    // error code category food : 130*
    Category_not_found(1301, "Category not found in database"),
    Id_category_not_null(1302, "Id Category not null"),

    // Error request Order : 140*
    IdOrder_not_found(1401, "Id Order not found"),
    Please_provide_more_data(1402, "Please provide more data"),
    Current_order_not_exist(1403, "Current order notexist"),
    Order_not_found(1404, "Order not found in database"),

 // Error request Promotion : 150*
    Promotion_already_exist(1501, "Promotion already exist in database"),
    Promotion_not_exist(1502, "Promotion not exist in database"),
    Promotion_not_found(1503, "Promotion not Found"),
    End_Date_not_valid(1504, "End Date not valid"),
    Name_promotion_not_blank(1505,"Name promotion not blank"),
    Discount_not_null(1506,"Discount_not_null"),

    //error payment
    Order_already_completed(1601,"Order_already_completed"),

    // error login
	USER_EXISTED(1701,"User existed"),
	USER_NOT_EXISTS(1702,"User not existed"),
	PASSWORD_IS_INCORRECT(1703,"Password is incorrect"),
    Deleted_USER(1704,"previously  deleted user"),
    PASSWORD_NOt_NULL(1705,"Password NOT NULL"),
    PASSWORD_CHANGED(1706,"PASSWORD CHANGED"),

    //error jwt
    Invalid_token(1801,"Invalid token"),
    OLD_TOKEN(1802,"old token"),
    UNAUTHENTICATED(1803,"UNAUTHENTICATED"),
    // error shift
    another_shift_working(1901,"there is another shift working"),
    there_is_not_any_shift_working(1901,"there is not any shift working"),
    wrong_cash_checkout(1902,"wrong cash when checkout"),
    Shift_not_exist(1901,"there is not any shift working"),
    Have_Order_Serving(1903,"Have Order Serving"),
    NOT_YOUR_SHIFT(1904,"Not your shift"),

            ;

    ;
    private int code;
    private String message;

    private ErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
