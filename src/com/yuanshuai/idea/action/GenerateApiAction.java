package com.yuanshuai.idea.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.*;
import com.yuanshuai.idea.config.ShowDocState;
import com.yuanshuai.idea.utils.NotificationUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;

import javax.naming.Name;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenerateApiAction extends AnAction {

    private String api;
    private String key;
    private String token;

    private Project project;

    private ShowDocState showDocState;

    @Override
    public void actionPerformed(@NotNull AnActionEvent actionEvent) {
        this.project = actionEvent.getProject();
        this.showDocState = ShowDocState.getInstance(this.project);
        this.buildParams();

        PsiFile psiFile = actionEvent.getDataContext().getData(CommonDataKeys.PSI_FILE);
        if (Objects.nonNull(psiFile)) {
            this.uploadApiByFile(psiFile);
            return;
        }

        PsiElement psiElement = actionEvent.getDataContext().getData(CommonDataKeys.PSI_ELEMENT);
        if (Objects.nonNull(psiElement)) {
            PsiDirectory psiDirectory = (PsiDirectory) psiElement; //PsiTreeUtil.getStubChildOfType(psiElement, PsiDirectory.class);
            PsiFile[] psiFiles = psiDirectory.getFiles();
            for (PsiFile file: psiFiles) {
                this.uploadApiByFile(file);
            }
            return;
        }

        System.out.println("NUll");
    }

    private void uploadApiByFile(PsiFile psiFile)
    {
        String fileContext = psiFile.getText();
        if (!fileContext.contains("showdoc")) {
            System.out.println(psiFile.getOriginalFile().getName() + "文件不包含showdoc关键字");
            return;
        }

        String patternStr = "(/\\*{1,2}[\\s\\S]*?\\*/)";
        Pattern pattern = Pattern.compile(patternStr, Pattern.MULTILINE | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(fileContext);

        int matcherStart = 0;

        StringBuilder showDocText = new StringBuilder();

        while (matcher.find(matcherStart)){
            showDocText.append("\t").append(matcher.group(1));
            matcherStart = matcher.end();
        }

        String showDocContent = showDocText.toString().replaceAll("[\r\n]", "");
        showDocContent = showDocContent.replaceAll("&", "_this_and_change_");

        String url = this.api.trim() + "/server/?s=/api/open/fromComments";

        String postData = "from=shell&api_key=" + this.key + "&api_token=" + this.token + "&content=" + showDocContent;

        CloseableHttpClient httpClient = HttpClients.createDefault();

        StringEntity stringEntity = new StringEntity(postData, StandardCharsets.UTF_8);
        System.out.println(stringEntity.toString());

        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(stringEntity);
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            if (!Objects.isNull(entity)) {
                String result = EntityUtils.toString(entity, "UTF-8");
                System.out.println("result：" + result);
            }
            System.out.println("233333");
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }

        String[] curlCommands={
                "curl", "-H", "'Content-Type: application/x-www-form-urlencoded; charset=UTF-8'",
                "\"" + url+ "\"", "--data-binary", "@-",
                "<<CURL_DATA\n" + "from=shell&api_key=" + this.key + "&api_token=" + this.token + "&content=" + showDocContent + "\nCURL_DATA"
        };

        // System.out.println(Arrays.toString(curlCommands));

        String result = execCurl(curlCommands);

        System.out.println("结果:" + result);
    }

    public static String execCurl(String[] command) {
        ProcessBuilder process = new ProcessBuilder(command);
        Process p;
        try {
            p = process.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            return builder.toString();
        } catch (IOException e) {
            System.out.print("error");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("errorMessage:" + e.getMessage());
        }
        return null;
    }

    private void buildParams()
    {
        this.api = this.showDocState.getApi();
        if (this.api.endsWith("/")) {
            this.api = this.api.substring(0,this.api.length()-1);
        }
        System.out.println("api:" + this.api);
        if (StringUtil.isEmpty(this.api)) {
            this.api = Messages.showInputDialog("Input Api", "Input Api", Messages.getWarningIcon());
            if (StringUtil.isEmpty(this.api)) {
                NotificationUtil.warnNotify("Api Is Empty", this.project);
                return;
            }
            this.showDocState.setApi(this.api);
        }

        this.key = this.showDocState.getKey();
        System.out.println("key:" + this.key);
        if (StringUtil.isEmpty(this.key)) {
            this.key = Messages.showInputDialog("Input Key", "Input Key", Messages.getWarningIcon());
            if (StringUtil.isEmpty(this.key)) {
                NotificationUtil.warnNotify("Key Is Empty", this.project);
                return;
            }
            this.showDocState.setKey(this.key);
        }

        this.token = this.showDocState.getToken();
        System.out.println("token:" + this.token);
        if (StringUtil.isEmpty(this.token)) {
            this.token = Messages.showInputDialog("Input Token", "Input Token", Messages.getWarningIcon());
            if (StringUtil.isEmpty(this.token)) {
                NotificationUtil.warnNotify("Token Is Empty", this.project);
                return;
            }
            this.showDocState.setToken(this.token);
        }
    }
}
