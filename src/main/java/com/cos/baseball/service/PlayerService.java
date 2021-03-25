package com.cos.baseball.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.baseball.domain.player.Player;
import com.cos.baseball.domain.player.PlayerRepository;
import com.cos.baseball.domain.team.Team;
import com.cos.baseball.domain.team.TeamRepository;
import com.cos.baseball.web.dto.player.PlayerInsertReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PlayerService {
	
	private final PlayerRepository playerRepository;
	private final TeamRepository teamRepository;
	
	@Transactional
	public int 선수등록(PlayerInsertReqDto playerInsertReqDto) {
		
		Team teamEntity = teamRepository.findByTeamName(playerInsertReqDto.getTeamName());
		
		if(teamEntity==null) {
			return -1;
		} else {
			Player player = Player.builder()
					.name(playerInsertReqDto.getName())
					.position(playerInsertReqDto.getPosition())
					.team(teamEntity)
					.build();
			
			playerRepository.save(player);
			
			return 1;
		}
	}
	
	public List<Player> 전체찾기() {
		return playerRepository.findAll();
	}
	
	@Transactional
	public void 삭제하기(int id) {
		playerRepository.deleteById(id);
	}
	
	public void 피벗받기() {
		StringBuffer sb = new StringBuffer();
		sb.append("select p.position as '포지션', ");
		sb.append("GROUP_CONCAT(case when t.teamName = '롯데자이언츠' ");
		sb.append("THEN p.name ELSE null END) as '롯데자이언츠', ");
		sb.append("GROUP_CONCAT(case when t.teamName = '삼성라이온즈' ");
		sb.append("THEN p.name ELSE null END) as '삼성라이온즈'");
		sb.append("GROUP_CONCAT(case when t.teamName = '기아타이거즈' ");
		sb.append("THEN p.name ELSE null END) as '기아타이거즈'");
		sb.append("from player p ");
		sb.append("inner join team t on p.teamId = t.id GROUP BY p.position;");
		String pivot = sb.toString();
	}
	
}
