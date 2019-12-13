package com.itheima.mapper;

public interface ReportMapper {


    Integer todayNewMember(String reportDate);

    Integer totalMember();

    Integer thisWeekNewMember(String string);

    Integer thisMonthNewMember(String s);
}
