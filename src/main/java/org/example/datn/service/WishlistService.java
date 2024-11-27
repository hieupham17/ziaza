package org.example.datn.service;

import org.example.datn.entity.SanPham;
import org.example.datn.entity.Wishlist;
import org.example.datn.model.UserAuthentication;
import org.example.datn.repository.SanPhamRepository;
import org.example.datn.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistService {
    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private SanPhamRepository productRepository;


    public List<SanPham> getFavoriteProductsByUserId(UserAuthentication ua) {
        List<Long> favoriteProductIds = wishlistRepository.findProductIdsByUserId(ua.getPrincipal());
        return productRepository.findAllById(favoriteProductIds);
    }

    public List<Wishlist> getAll() {
        return wishlistRepository.findAll();
    }

    @Transactional
    public boolean removeProductFromWishlist(Long productId, UserAuthentication ua) {
        // Tìm sản phẩm yêu thích theo productId và userId
        List<Wishlist> wishlist = wishlistRepository.findByIdAndUserId(productId, ua.getPrincipal());

        // Nếu danh sách có sản phẩm yêu thích, xóa sản phẩm
        if (!wishlist.isEmpty()) {
            wishlistRepository.delete(wishlist.get(0)); // Xóa sản phẩm đầu tiên (nếu có)
            return true;
        }

        // Nếu không tìm thấy sản phẩm yêu thích, trả về false
        return false;
    }

    @Transactional
    public boolean addProductToWishlist(Long productId, UserAuthentication ua) {
        // Kiểm tra xem sản phẩm đã có trong danh sách yêu thích của người dùng chưa
        List<Wishlist> existingWishlist = wishlistRepository.findByIdAndUserId(productId, ua.getPrincipal());
        if (!existingWishlist.isEmpty()) {
            // Nếu sản phẩm đã có trong danh sách yêu thích, trả về false
            System.out.println("Product is already in wishlist.");
            return false;
        }
        // Nếu sản phẩm chưa có trong danh sách yêu thích, thêm sản phẩm vào wishlist
        Wishlist wishlist = new Wishlist();
        wishlist.setProductId(productId);
        wishlist.setUserId(ua.getPrincipal());
        wishlistRepository.save(wishlist);
        return true;
    }
    public boolean isProductInWishlist(Long productId, UserAuthentication ua) {
        List<Wishlist> existingWishlist = wishlistRepository.findByIdAndUserId(productId, ua.getPrincipal());
        return !existingWishlist.isEmpty(); // Trả về true nếu sản phẩm có trong danh sách yêu thích, false nếu không
    }

}
