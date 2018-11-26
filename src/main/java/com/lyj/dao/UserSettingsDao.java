package com.lyj.dao;

import com.lyj.entity.User;
import com.lyj.entity.UserSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by 陆英杰
 * 2018/9/25 9:42
 */
public interface UserSettingsDao extends JpaRepository<UserSettings,Integer>{


    public UserSettings findByUser_Id(Integer userId);

    @Query(value = "select s.*,f.name as defaultFolderName from usersettings s left join folder f on " +
            "s.defaultFolderId=f.id where s.user_id=:userId",nativeQuery = true)
    public Map findIdAndNameByUser_Id(@Param("userId") Integer userId);


}
