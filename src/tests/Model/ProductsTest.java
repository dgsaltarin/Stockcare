package Model;

public class ProductsTest {

    @org.junit.Test
    public void showProducts() {
        Products products = new Products();
        products.showProducts("medicamento");
    }
}