package com.limai.video.dao;

import com.limai.domain.Video;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoMapper {
    @Select("select * from video where id=#{id}")
    Video findById(@Param("id") int id);
}
