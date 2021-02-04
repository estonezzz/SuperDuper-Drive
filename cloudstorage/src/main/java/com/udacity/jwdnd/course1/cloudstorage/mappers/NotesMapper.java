package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {
    @Select("SELECT * FROM NOTES WHERE userid = #{userID}")
    List<Note> showAllNotes(int userID);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES (#{noteTitle},#{noteDescription}, #{userID})")
    @Options(useGeneratedKeys = true, keyProperty = "noteID")
    int addNote(Note note);

    @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE noteid = #{noteID}")
    int updateNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid=#{noteID}")
    int deleteNote(@Param("noteID") int noteID);
}
