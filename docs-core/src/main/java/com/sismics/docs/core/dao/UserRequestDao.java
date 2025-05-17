package com.sismics.docs.core.dao;

import com.sismics.docs.core.model.jpa.UserRequest;
import com.sismics.util.context.ThreadLocalContext;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * DAO for UserRequest.
 */
public class UserRequestDao {

    /**
     * Create a user request.
     */
    public void create(UserRequest userRequest) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        em.persist(userRequest);
    }

    /**
     * Get all pending user requests.
     */
    public List<UserRequest> findPending() {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        TypedQuery<UserRequest> query = em.createQuery(
                "select ur from UserRequest ur where ur.status = :status order by ur.createDate desc",
                UserRequest.class);
        query.setParameter("status", "PENDING");
        return query.getResultList();
    }

    /**
     * Get a user request by ID.
     */
    public UserRequest getById(String id) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        try {
            return em.createQuery("select ur from UserRequest ur where ur.id = :id", UserRequest.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * Update a user request.
     */
    public void update(UserRequest userRequest) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        em.merge(userRequest);
    }
}
