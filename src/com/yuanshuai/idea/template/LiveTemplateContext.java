package com.yuanshuai.idea.template;

import com.intellij.codeInsight.template.TemplateContextType;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class LiveTemplateContext extends TemplateContextType {

    protected LiveTemplateContext() {
        super("PHP", "php");
    }

    @Override
    public boolean isInContext(@NotNull PsiFile psiFile, int offset) {
        return psiFile.getName().endsWith(".php");
    }
}
