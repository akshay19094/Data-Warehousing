package com.FinalInfo;
import javax.swing.*;
import java.sql.Connection;

public class DataObjectWithinDataObject extends JFrame {
    public DataObjectWithinDataObject(String projectID)
    {
        com.FinalInfo.DatabaseConnection dbcon=new com.FinalInfo.DatabaseConnection();
        Connection conn=dbcon.getConnection(projectID);
        dbQueries dbq=new dbQueries(conn);
        String p_info_list[]=dbq.fetch_pInfo_FromDataBase(projectID);

    }
};