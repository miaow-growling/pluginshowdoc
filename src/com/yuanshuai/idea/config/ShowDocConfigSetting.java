package com.yuanshuai.idea.config;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

class ShowDocConfigSetting implements Configurable {
    /**
     * api地址
     */
    JBTextField apiText;
    /**
     * key配置
     */
    JBTextField keyText;
    /**
     * token配置
     */
    JBTextField tokenText;

    /**
     * 持久化配置
     */
    private ShowDocState showDocState;

    private Project project;

    public ShowDocConfigSetting(Project project)
    {
        this.project = project;
        this.showDocState = ShowDocState.getInstance(this.project);
    }

    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return "showDocConfig";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        GridBagLayout layout = new GridBagLayout();
        JPanel settingPanel = new JPanel(layout);

        settingPanel.add(buildLabel("API"));
        apiText = buildTextField();
        apiText.setText(showDocState.getApi());
        layout.setConstraints(apiText, getValueConstraints());
        settingPanel.add(apiText);

        settingPanel.add(buildLabel("KEY"));
        keyText = buildTextField();
        keyText.setText(showDocState.getKey());
        layout.setConstraints(keyText, getValueConstraints());
        settingPanel.add(keyText);

        settingPanel.add(buildLabel("TOKEN"));
        tokenText = buildTextField();
        tokenText.setText(showDocState.getToken());
        layout.setConstraints(tokenText, getValueConstraints());
        settingPanel.add(tokenText);

        return settingPanel;
    }

    private JBLabel buildLabel(String name) {
        JBLabel label = new JBLabel(name);
        label.setPreferredSize(new Dimension(50, 30));
        return label;
    }

    private JBTextField buildTextField()
    {
        JBTextField textField = new JBTextField();
        textField.setPreferredSize(new Dimension(300, 30));
        return textField;
    }

    private GridBagConstraints getValueConstraints() {
        GridBagConstraints textConstraints = new GridBagConstraints();
        textConstraints.fill = GridBagConstraints.WEST;
        textConstraints.gridwidth = GridBagConstraints.REMAINDER;
        return textConstraints;
    }

    @Override
    public boolean isModified() {
        if (!apiText.getText().equals(showDocState.getApi())) return true;
        if (!keyText.getText().equals(showDocState.getKey())) return true;
        return !tokenText.getText().equals(showDocState.getToken());
    }

    @Override
    public void apply() throws ConfigurationException {
        System.out.println(apiText.getText());
        showDocState.setApi(apiText.getText());
        System.out.println(keyText.getText());
        showDocState.setKey(keyText.getText());
        System.out.println(tokenText.getText());
        showDocState.setToken(tokenText.getText());
    }
}