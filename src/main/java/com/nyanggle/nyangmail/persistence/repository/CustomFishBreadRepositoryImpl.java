package com.nyanggle.nyangmail.persistence.repository;

import com.nyanggle.nyangmail.interfaces.dto.fishbread.FishBreadListResDto;
import com.nyanggle.nyangmail.interfaces.dto.fishbread.QFishBreadListResDto;
import com.nyanggle.nyangmail.interfaces.dto.fishbread.SearchCondition;

import com.nyanggle.nyangmail.persistence.entity.FishBreadStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.nyanggle.nyangmail.persistence.entity.QFishBread.fishBread;
@Repository
@RequiredArgsConstructor
public class CustomFishBreadRepositoryImpl implements CustomFishBreadRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<FishBreadListResDto> searchByCondition(String uUid, SearchCondition searchCondition, Pageable pageable) {
        Long count = jpaQueryFactory.select(fishBread.count())
                .from(fishBread)
                .where(createSearchCondition(searchCondition, uUid))
                .fetchOne();

        List<FishBreadListResDto> fishBreads =
                jpaQueryFactory.select(
                        new QFishBreadListResDto(fishBread.id, fishBread.type, fishBread.senderNickname, fishBread.status)
                    ).from(fishBread)
                    .where( startFromFishId(searchCondition.getFishId(), searchCondition.getCallType())
                            , createSearchCondition(searchCondition, uUid)
                    )
                    .orderBy(fishBread.id.desc())
                    .limit(pageable.getPageSize())
                    .fetch();
        return new PageImpl<>(fishBreads, pageable, count);
    }

    /**
     * 페이지네이션 noOffSet 방식
     * 기본 정렬 방식은 최신순이기 때문에
     * prev면 id보다 큰 값을 가져와야 함.
     */
    private BooleanExpression startFromFishId(Long fishId, String callType) {
        if(fishId == 0) {
            return null;
        }
        if(callType.equals("prev")) {
            return fishBread.id.gt(fishId);
        }
        return fishBread.id.lt(fishId);
    }

    private BooleanExpression createSearchCondition(SearchCondition searchCondition, String uUid) {
        BooleanExpression expression = fishBread.status.ne(FishBreadStatus.DELETED);
        if(searchCondition.getStatus() != null) {
            expression = expression.and(fishBread.status.eq(searchCondition.getStatus()));
        }
        if(StringUtils.hasText(uUid)) {
            expression = expression.and(fishBread.receiverUid.eq(uUid));
        }
        return expression;
    }
}
