package com.lyj.dao;

import com.lyj.entity.UserSettings;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * Created by 陆英杰
 * 2018/9/25 9:42
 */
@Repository
public interface UserSettingsDao{

//    @Select("select u.id,u.defaultFolderId,u.userId,f.name as folderName from usersettings u left join folder f on u.defaultFolderId=f.id where u.id=#{id}")
//    UserSettings getUserSettingsById(Integer id);

    @Select("select u.id,u.defaultFolderId,u.userId,f.name as defaultFolderName from usersettings u left join folder f on u.defaultFolderId=f.id where u.userId=#{userId}")
    UserSettings getUserSettingsByUserId(Integer userId);

    @Update("update usersettings set defaultFolderId=#{defaultFolderId} where userId=#{userId}")
    int updateDefaultFolderId(@Param("defaultFolderId") int defaultFolderId, @Param("userId")int userId);

    @Insert("insert into usersettings (defaultFolderId,userId) values (#{defaultFolderId},#{userId})")
    void addUserSettings(UserSettings userSettings);

//    @Update("update usersettings set defaultFolderId=#{defaultFolderId} where userId=#{userId}")
//    int update(UserSettings userSettings,@Param("userId")int userId);


//    public UserSettings findByUser_Id(Integer userId);

//    @Query(value = "select s.*,f.name as defaultFolderName from usersettings s left join folder f on " +
//            "s.defaultFolderId=f.id where s.user_id=:userId",nativeQuery = true)
//    public Map findIdAndNameByUser_Id(@Param("userId") Integer userId);


}
