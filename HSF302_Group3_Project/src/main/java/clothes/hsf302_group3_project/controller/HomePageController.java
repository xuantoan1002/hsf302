package clothes.hsf302_group3_project.controller;

import clothes.hsf302_group3_project.dto.CategoryDTO;
import clothes.hsf302_group3_project.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomePageController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping()
    public String home(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String searchQuery,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            Model model) {

        List<CategoryDTO> categories = categoryService.getAllCategoriesWithProductCount();
        model.addAttribute("categories", categories);
        model.addAttribute("currentCategoryId", categoryId);
        model.addAttribute("searchQuery", searchQuery);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("direction", direction);

        List<ProductDTO> products;


        if (searchQuery != null && !searchQuery.isEmpty()) {
            // Tìm kiếm sản phẩm với partial matching
            products = productService.searchProducts(searchQuery);
            products = productService.sortProducts(products, sortBy, direction);
            model.addAttribute("isSearchResult", true);
        } else if (categoryId != null) {
            // Lọc theo danh mục
            products = productService.getProductsByCategoryAndSort(categoryId, sortBy, direction);
            model.addAttribute("isSearchResult", false);
        } else {
            // Xem tất cả sản phẩm hoặc sản phẩm nổi bật
            if (sortBy.equals("id") && direction.equals("asc")) {
                products = productService.getFeaturedProducts();
            } else {
                products = productService.getAllProductsSorted(sortBy, direction);
            }
            model.addAttribute("isSearchResult", false);
        }

        model.addAttribute("products", products);
        return "HomePage";
    }

    @GetMapping("/product/{id}")
    public String productDetail(@PathVariable Integer id, Model model) {
        ProductDTO productDTO = productService.getProductDetail(id);
        List<CategoryDTO> categories = categoryService.getAllCategoriesWithProductCount();

        model.addAttribute("product", productDTO);
        model.addAttribute("categories", categories);
        return "productDetail";
    }

    @PostMapping("/add-product-to-cart/{id}")
    public String addProductToCart(@PathVariable long id, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        long productId = id;
        String email = "tqt@gmail.com";
//        String email = (String) session.getAttribute("email");
        this.cartService.handleAddProductToCart(email, productId, session, 1l);
        return "redirect:/";
    }
}
