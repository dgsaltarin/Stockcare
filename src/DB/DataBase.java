package DB;

public class DataBase {

    public static final String URL = "jdbc:mysql://localhost:3306/";
    public static final String DB = "stockcare";
    public static final String USER = "root";
    public static final String PASSWORD = "admin";

    public static final String TINVENTARIO = "inventario";
    public static final String TINVENTARIO_ID = "id";
    public static final String TINVENTARIO_CANTIDAD = "cantidad_inventario";
    public static final String TINVENTARIO_VENCIMIENTO = "fecha_vencimiento";
    public static final String TINVENTARIO_PRODUCTOID = "productos_id";

    public static final String TPRODUCTOS = "productos";
    public static final String TPRODUCTOS_NOMBRE = "nombre_producto";
    public static final String TPRODUCTOS_CODIGO = "id";
    public static final String TPRODUCTOS_PRECIO = "precio_producto";
    public static final String TPRODUCTOS_TIPO = "tipo_producto";

    public static final String TUSUARIOS = "usuarios";
    public static final String TUSUARIOS_ID = "usuarios";
    public static final String TUSUARIOS_NOMBRE= "usuarios";
    public static final String TUSUARIOS_TIPO = "usuarios";

    public static final String TAREAS = "areas";
    public static final String TAREAS_NOMBRE = "id";
    public static final String TAREAS_ID = "nombre_area";

    public static final String TPROVEEDORES = "proveedores";
    public static final String TPROVEEDORES_ID = "id";
    public static final String TPROVEEDORES_NOMBRE = "nombre_proveedor";
    public static final String TPROVEEDORES_EMAIL = "email";
    public static final String TPROVEEDORES_DIRECCIÓN = "direccion";
    public static final String TPROVEEDORES_NIT = "NIT";
    public static final String TPROVEEDORES_TELEFONO = "telefono";
}

