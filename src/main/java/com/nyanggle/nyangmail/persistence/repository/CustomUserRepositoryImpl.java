package com.nyanggle.nyangmail.persistence.repository;

import com.nyanggle.nyangmail.interfaces.dto.UserStatus;
import static com.nyanggle.nyangmail.persistence.entity.QUser.user;
import com.nyanggle.nyangmail.persistence.entity.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomUserRepositoryImpl implements CustomUserRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<User> findByNormalUser(String domesticId, String providerType) {
        User user1 = jpaQueryFactory.selectFrom(user)
                .where(standardNormalUser(domesticId, providerType))
                .fetchOne();
        return Optional.of(
                user1
        );
    }

    private BooleanExpression standardNormalUser(String domesticId, String providerType) {
        BooleanExpression expression = user.id.gt(0);
        expression = expression.and(user.domesticId.eq(domesticId))
                .and(user.providerType.eq(providerType))
                .and(user.status.eq(UserStatus.NORMAL));
        return expression;
    }
}
