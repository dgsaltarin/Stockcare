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
    public static final String TPRODUCTOS_TIPO = "tipo_de_producto";
    public static final String TPRODUCTOS_CLASIFICACION = "clasificacion_ven";
    public static final String TPRODUCTOS_NOMBRE_EXTERNO = "productos.nombre_producto";

    public static final String TUSUARIOS = "usuarios";
    public static final String TUSUARIOS_ID = "id";
    public static final String TUSUARIOS_NOMBRE= "nombre_usuarios";
    public static final String TUSUARIOS_TIPO = "usuarios";

    public static final String TAREAS = "areas";
    public static final String TAREAS_NOMBRE = "nombre_area";
    public static final String TAREAS_ID = "id";

    public static final String TPROVEEDORES = "proveedores";
    public static final String TPROVEEDORES_ID = "id";
    public static final String TPROVEEDORES_CIUDAD = "ciudad";
    public static final String TPROVEEDORES_NOMBRE = "nombre_proveedor";
    public static final String TPROVEEDORES_EMAIL = "email";
    public static final String TPROVEEDORES_DIRECCIÓN = "direccion";
    public static final String TPROVEEDORES_NIT = "NIT";
    public static final String TPROVEEDORES_TELEFONO = "telefono";

    public static final String TSALIDAS = "salidas";
    public static final String TSALIDAS_ID = "id";
    public static final String TSALIDAS_FECHA = "fecha_salida";
    public static final String TSALIDAS_CANTIDAD = "cantidad_salida";
    public static final String TSALIDAS_PRODUCTO = "productos_id";
    public static final String TSALIDAS_USUARIO = "usuarios_id";
    public static final String TSALIDAS_AREA = "areas_id";

    public static final String TENTRADAS = "entradas";
    public static final String TENTRADAS_ID = "id";
    public static final String TENTRADAS_FECHA = "fecha_entrada";
    public static final String TENTRADAS_CANTIDAD = "cantidad_entrada";
    public static final String TENTRADAS_PRODUCTO = "productos_id";
    public static final String TENTRADAS_USUARIO = "Usuarios_id";
    public static final String TENTRADAS_PROVEEDOR = "proveedores_id";

    public static final String TORDEN_COMPRA = "orden_compra";
    public static final String TORDEN_COMPRA_ID = "id";
    public static final String TORDEN_COMPRA_NUMERO = "numero_orden";
    public static final String TORDEN_COMPRA_CANTIDAD = "cantidad";
    public static final String TORDEN_COMPRA_PROVEEDOR = "proveedor_id";
    public static final String TORDEN_COMPRA_PRODUCTO = "producto_id";

    public static final String THOSPITAL = "hospital";
    public static final String THOSPITAL_NOMBRE = "nombre";
    public static final String THOSPITAL_NIT = "NIT";
    public static final String THOSPITAL_DIRECCION = "direccion";
    public static final String THOSPITAL_TELEFONO = "telefono";
    public static final String THOSPITAL_CIUDAD = "ciudad";
    public static final String THOSPITAL_EMAIL = "email";
}

