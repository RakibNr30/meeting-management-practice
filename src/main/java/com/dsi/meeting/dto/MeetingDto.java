package com.dsi.meeting.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class MeetingDto {
    private String title;
    private List<Long> userIds;
}
