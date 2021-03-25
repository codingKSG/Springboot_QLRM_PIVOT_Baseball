package com.cos.baseball.web.dto.team;

import com.cos.baseball.domain.team.Team;

import lombok.Builder;
import lombok.Data;

@Data
public class TeamInsertReqDto {
	
	private String teamName;
	
	public Team toEntity() {
		return Team.builder()
				.teamName(teamName)
				.build();
	}
}
