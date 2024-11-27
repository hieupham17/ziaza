package org.example.datn.jwt;

import org.example.datn.model.Pair;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.Filter;
import java.util.List;

public interface FilterConfigRegister {
    List<Pair<GenericFilterBean, Class<? extends Filter>>> getBeforeFilterRegistered();
    List<Pair<GenericFilterBean, Class<? extends Filter>>> getFilterRegistered();
    List<Pair<GenericFilterBean, Class<? extends Filter>>> getAfterFilterRegistered();
}
