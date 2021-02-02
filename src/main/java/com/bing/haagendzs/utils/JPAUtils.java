package com.bing.haagendzs.utils;

import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class JPAUtils {

    @PersistenceContext
    private EntityManager entityManager;

    public <T> List<T> runNativeSql(String sql, Class<T> cls) {
        NativeQueryImpl nql = entityManager.createNativeQuery(sql).unwrap(NativeQueryImpl.class);
        nql.setResultTransformer(Transformers.aliasToBean(cls));
        return (List<T>) nql.getResultList();
    }
}