package com.parkingadmin.repository;

import com.parkingadmin.entity.Activity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.parkingadmin.entity.ActivityType;

import java.time.Instant;
import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findTop4ByOrderByOccurredAtDesc();

    // Registros de un tipo en [from, to).
    @Query("select count(a) from Activity a where a.type = :type and a.occurredAt >= :from and a.occurredAt < :to")
    long countByTypeBetween(@Param("type") ActivityType type, @Param("from") Instant from, @Param("to") Instant to);

    // Promedio de stay_minutes de un tipo en [from, to); null si no hay registros con estadía.
    @Query("select avg(a.stayMinutes) from Activity a where a.type = :type and a.occurredAt >= :from and a.occurredAt < :to")
    Double averageStayBetween(@Param("type") ActivityType type, @Param("from") Instant from, @Param("to") Instant to);

    // Registros posteriores (más antiguos) al cursor (occurredAt, id), en el orden de /api/activity.
    @Query("select a from Activity a where a.occurredAt < :occurredAt or (a.occurredAt = :occurredAt and a.id < :id) "
            + "order by a.occurredAt desc, a.id desc")
    List<Activity> findAfterCursor(@Param("occurredAt") Instant occurredAt, @Param("id") Long id, Pageable page);
}
