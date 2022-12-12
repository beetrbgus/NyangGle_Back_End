package com.nyanggle.nyangmail.user.repository;

import com.nyanggle.nyangmail.user.persistence.UserStatus;
import static com.nyanggle.nyangmail.persistence.entity.QUser.user;
import com.nyanggle.nyangmail.user.persistence.User;
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
        return Optional.ofNullable(
                jpaQueryFactory.selectFrom(user)
                    .where(standardNormalUser(domesticId, providerType))
                    .fetchOne()
        );
    }

    @Override
    public Optional<User> findByUserUid(String userId) {
        return Optional.of(
                jpaQueryFactory.selectFrom(user)
                    .where(user.userUid.eq(userId)
                            , defaultCondition())
                    .fetchOne()
        );
    }
    private BooleanExpression defaultCondition() {
        return user.status.eq(UserStatus.NORMAL);
    }
    private BooleanExpression standardNormalUser(String domesticId, String providerType) {
        BooleanExpression expression = user.id.gt(0);
        expression = expression.and(user.domesticId.eq(domesticId))
                .and(user.providerType.eq(providerType))
                .and(user.status.eq(UserStatus.NORMAL));
        return expression;
    }
}
