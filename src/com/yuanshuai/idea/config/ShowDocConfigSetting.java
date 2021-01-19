package com.yuanshuai.idea.config;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

class ShowDocConfigSetting implements Configurable {
    JBTextField apiText;
    JBTextField keyText;
    JBTextField tokenText;

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
        apiText = new JBTextField();
        layout.setConstraints(apiText, getValueConstraints());
        settingPanel.add(apiText);

        settingPanel.add(buildLabel("KEY"));
        keyText = new JBTextField();
        layout.setConstraints(keyText, getValueConstraints());
        settingPanel.add(keyText);

        settingPanel.add(buildLabel("TOKEN"));
        tokenText = new JBTextField();
        layout.setConstraints(tokenText, getValueConstraints());
        settingPanel.add(tokenText);

        return settingPanel;
    }

    private JBLabel buildLabel(String name)
    {
        JBLabel label = new JBLabel(name);
        return label;
    }

    private GridBagConstraints getValueConstraints() {
        GridBagConstraints textConstraints = new GridBagConstraints();
        textConstraints.fill = GridBagConstraints.WEST;
        textConstraints.gridwidth = GridBagConstraints.REMAINDER;
        return textConstraints;
    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {
        System.out.println("apply");
    }
}