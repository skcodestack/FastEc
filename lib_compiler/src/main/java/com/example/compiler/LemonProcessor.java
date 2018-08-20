package com.example.compiler;

import com.example.annatations.AppRegisterGenerator;
import com.example.annatations.EntryGenerator;
import com.example.annatations.PayEntryGenerator;
import com.google.auto.service.AutoService;

import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.AnnotationValueVisitor;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/12
 * Version  1.0
 * Description: create code(libray or application is no use)
 */

//create meta info
@AutoService(Processor.class)
public class LemonProcessor extends AbstractProcessor {


    /**
     * 支持的注解类类型
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        for (Class<? extends Annotation> annotation : getSupportedAnnotations()) {
            types.add(annotation.getCanonicalName());
        }
        return types;
    }

    /**
     * support annotation
     *
     * @return
     */
    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        annotations.add(EntryGenerator.class);
        annotations.add(PayEntryGenerator.class);
        annotations.add(AppRegisterGenerator.class);
        return annotations;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment env) {

        generaterEntrySouceCode(env);
        generaterPayEntrySouceCode(env);
        generaterAppRigsterSouceCode(env);

        return false;
    }

    private void scan(RoundEnvironment env, Class<? extends Annotation> annotationClass, AnnotationValueVisitor visitor) {
        for (Element itemElement : env.getElementsAnnotatedWith(annotationClass)) {
            final List<? extends AnnotationMirror> annotationMirrors = itemElement.getAnnotationMirrors();
            for (AnnotationMirror mirror : annotationMirrors) {
                Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = mirror.getElementValues();
                for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementValues.entrySet()) {
                    entry.getValue().accept(visitor, null);
                }

            }
        }
    }

    private void generaterEntrySouceCode(RoundEnvironment env) {
        final EntryVisitor visitor = new EntryVisitor(processingEnv.getFiler());
        scan(env, EntryGenerator.class, visitor);
    }

    private void generaterPayEntrySouceCode(RoundEnvironment env) {
        final PayEntryVisitor visitor = new PayEntryVisitor(processingEnv.getFiler());
        scan(env, PayEntryGenerator.class, visitor);
    }

    private void generaterAppRigsterSouceCode(RoundEnvironment env) {
        final AppRegisterVisitor visitor = new AppRegisterVisitor(processingEnv.getFiler());
        scan(env, AppRegisterGenerator.class, visitor);
    }
}
