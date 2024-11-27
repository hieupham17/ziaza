//package org.example.datn.impl;
//
//import org.example.datn.entity.Blog;
//import org.example.datn.repository.BlogRepository;
//import org.example.datn.service.BlogService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class BlogServiceImpl  {
//
//    @Autowired
//    BlogRepository blogRepository;
//
//    @Override
//    public List<Blog> findAll() {
//        return blogRepository.findAll();
//    }
//
//    @Override
//    public Blog findById(Long id) {
//        return blogRepository.findById(id).get();
//    }
//
//    @Override
//    public List<Blog> findByCateId(Long cid) {
//        return blogRepository.findByCateId(cid);
//    }
//    @Override
//    public Blog create(Blog product) {
//        return blogRepository.save(product);
//    }
//
//    @Override
//    public Blog update(Blog product) {
//        return blogRepository.save(product);
//    }
//
//    @Override
//    public void delete(Long id) {
//        blogRepository.deleteById(id);
//    }
//
//}
