package com.prography.playeasy.match.domain;

import android.util.Log;

import com.prography.playeasy.lib.RetrofitClientFactory;
import com.prography.playeasy.match.api.RetrofitMatchApi;
import com.prography.playeasy.match.domain.dtos.MatchDto;
import com.prography.playeasy.match.domain.dtos.request.MatchPostRequestDto;
import com.prography.playeasy.match.domain.dtos.response.MatchDetailDto;
import com.prography.playeasy.match.domain.dtos.response.MatchListDto;
import com.prography.playeasy.match.domain.models.Match;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Match 에 대한 API에 접근하는 객체(보통은 DB에 접근할 때 쓰기 때문에, DAO가 올바른 표현은 아닐 수 있음.)
 * 웹 프로젝트에서는 ApiClient라는 이름으로 쓰기도 하지만, 이 DAO는 Match 관련된 데이터만 접근 할 것이므로 MatchDao로 명명함.
 //file 하나는 목적이 하나

 */
public class MatchDao {

    private RetrofitMatchApi matchClient;
    private String token;

    public MatchDao(String token) {
        this.matchClient = RetrofitClientFactory.getClient(RetrofitMatchApi.class);
        this.token = token;
    }

    public Match create(MatchPostRequestDto requestDto) throws IOException {
        Call<MatchDetailDto> call = matchClient.postMatch(token, requestDto);
        try {
            Response<MatchDetailDto> response = call.execute();
            MatchDetailDto matchDetailDto = response.body();
            assert matchDetailDto != null;
            return matchDetailDto.getMatch();
        } catch (Throwable e) {
            Log.e("CREATE_FAIL", Objects.requireNonNull(e.getMessage()));
            throw e;
        }
    }

    public List<MatchDto> retrieve(Date date) throws IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Call<List<MatchDto>> call = matchClient.getMatchList(formatter.format(date));
        List<MatchDto> matchListDto;
        try {
            Response<List<MatchDto>> response = call.execute();
            matchListDto = response.body();
            assert matchListDto != null;
            return matchListDto;
        } catch (Throwable e) {
            Log.e("RETRIEIVE_FAIL", Objects.requireNonNull(e.getMessage()));
            throw e;
        }
    }

    public Match findById(int matchId) throws IOException {
        Call<MatchDetailDto> call = matchClient.getMatch(matchId);
        try {
            Response<MatchDetailDto> response = call.execute();
            MatchDetailDto matchDetailDto = response.body();
            assert matchDetailDto != null;
            return matchDetailDto.getMatch();
        } catch (Throwable e) {
            Log.e("FIND_FAIL", Objects.requireNonNull(e.getMessage()));
            throw e;
        }
    }

    public static void createSampleMatch() throws ParseException {

        List<MatchDto> matchArr = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


        matchArr.add(new MatchDto("SOCCER", "축구뜨자", sdf.parse("2020-07-04"), 180, 3000, "010-9165-6918", 11));
        matchArr.add(new MatchDto("FOOTSAL5", "풋살 즐기", sdf.parse("2020-07-04"), 180, 5000, "010-9165-6918", 5));

    }
//    public Match reviseMatch
//
//
//            public void closeMatch()
//            {
//
//
//
//            }
}