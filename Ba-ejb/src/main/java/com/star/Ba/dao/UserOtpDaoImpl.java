package com.star.Ba.dao;



import java.util.Arrays;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.star.Ba.commun.OtpStatus;
import com.star.Ba.commun.OtpType;
import com.star.Ba.model.UserOtp;


@Stateless
public class UserOtpDaoImpl extends AbstractDAO<UserOtp> implements UserOtpDao {
	
	
	@PersistenceContext(unitName = "oracle")
    private EntityManager entityManager;

    public UserOtpDaoImpl() {
    	super(UserOtp.class);
	}
    
	public UserOtpDaoImpl(Class<UserOtp> entityClass) {
		super(entityClass);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}


    @Override
    public void save(UserOtp otp) {

        entityManager.persist(otp);
    }
    
    @Override
    public void update(UserOtp otp) {

        entityManager.merge(otp);
    }

//    @Override
//    public UserOtp findLastActiveOtp(
//            Long userId,
//            OtpType otpType
//    ) {
//
//        TypedQuery<UserOtp> query =
//                entityManager.createQuery(
//                        "SELECT o FROM UserOtp o " +
//                        "WHERE o.userId = :userId " +
//                        "AND o.otpType = :otpType " +
//                        "AND o.status = :status " +
//                        "ORDER BY o.createdAt DESC",
//                        UserOtp.class
//                );
//
//        query.setParameter("userId", userId);
//
//        query.setParameter( "otpType", otpType );
//        
//        query.setParameter(
//                "status",
//                OtpStatus.PENDING
//        );
//
//        query.setMaxResults(1);
//
//        try {
//
//            return query.getSingleResult();
//
//        } catch (Exception e) {
//
//            return null;
//        }
//    }
    
    
    
    @Override
    public UserOtp findLastPendingOtp(
            Long userId,
            OtpType otpType
    ) {

        TypedQuery<UserOtp> query =
                entityManager.createQuery(
                        "SELECT o FROM UserOtp o " +
                        "WHERE o.userId = :userId " +
                        "AND o.otpType = :otpType " +
                        "AND o.status = :status " +
                        "ORDER BY o.createdAt DESC",
                        UserOtp.class
                );

        query.setParameter( "userId", userId );

        query.setParameter( "otpType", otpType );

        query.setParameter( "status", OtpStatus.PENDING );

        query.setMaxResults(1);

        try {

            return query.getSingleResult();

        } catch (Exception e) {

            return null;
        }
    }

	@Override
	public UserOtp findLastValidatedOtp(Long userId, OtpType otpType) {
	     TypedQuery<UserOtp> query =
	                entityManager.createQuery(
	                        "SELECT o FROM UserOtp o " +
	                        "WHERE o.userId = :userId " +
	                        "AND o.otpType = :otpType " +
	                        "AND o.status = :status " +
	                        "ORDER BY o.validatedAt  DESC",
	                        UserOtp.class
	                );

	        query.setParameter("userId", userId);

	        query.setParameter( "otpType", otpType );
	        
	        query.setParameter(
	                "status",
	                OtpStatus.VALIDATED
	        );

	        query.setMaxResults(1);

	        try {

	            return query.getSingleResult();

	        } catch (Exception e) {

	            return null;
	        }
	}

	
	
	@Override
	public UserOtp findLastPendingOrValidatedOtp(
	        Long userId,
	        OtpType otpType
	) {

	    TypedQuery<UserOtp> query =
	            entityManager.createQuery(
	                    "SELECT o FROM UserOtp o " +
	                    "WHERE o.userId = :userId " +
	                    "AND o.otpType = :otpType " +
	                    "AND (o.status = :pending OR o.status = :validated) " +
	                    "ORDER BY " +
	                    "CASE " +
	                    "WHEN o.status = :validated THEN o.validatedAt " +
	                    "ELSE o.createdAt " +
	                    "END DESC",
	                    UserOtp.class
	            );

	    query.setParameter("userId", userId);
	    query.setParameter("otpType", otpType);
	    query.setParameter("pending", OtpStatus.PENDING);
	    query.setParameter("validated", OtpStatus.VALIDATED);

	    query.setMaxResults(1);

	    try {
	        return query.getSingleResult();
	    } catch (NoResultException e) {
	        return null;
	    }
	}




 
}