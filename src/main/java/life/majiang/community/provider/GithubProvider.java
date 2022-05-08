package life.majiang.community.provider;

import com.alibaba.fastjson.JSON;
import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO)
    {
        String url = "https://github.com/login/oauth/access_token";
        MediaType mediaType = MediaType.get("application/json;charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try(Response response = client.newCall(request).execute())
        {
            String s = response.body().string();
            System.out.println(s);
            return s;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getGithubUser(String accessToken)
    {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://api.github.com/user?access_token" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string,GithubUser.class);
            return  githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
