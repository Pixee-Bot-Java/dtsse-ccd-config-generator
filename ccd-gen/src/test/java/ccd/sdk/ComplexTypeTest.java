package ccd.sdk;

import ccd.sdk.generator.ConfigGenerator;
import ccd.sdk.generator.Builder;
import ccd.sdk.types.ComplexType;
import ccd.sdk.generator.DisplayContext;
import com.google.common.io.Resources;
import org.junit.Test;
import org.reflections.Reflections;
import org.skyscreamer.jsonassert.JSONAssert;
import uk.gov.hmcts.reform.fpl.model.CaseData;
import uk.gov.hmcts.reform.fpl.model.CaseState;
import uk.gov.hmcts.reform.fpl.model.HearingBooking;
import uk.gov.hmcts.reform.fpl.model.Solicitor;
import uk.gov.hmcts.reform.fpl.model.common.Element;

import javax.validation.constraints.NotNull;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static ccd.sdk.generator.Builder.builder;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class ComplexTypeTest {
    @Test
    public void solicitor() throws Exception {

        Reflections reflections = new Reflections("uk.gov.hmcts");
        Set<Class<?>> types = reflections.getTypesAnnotatedWith(ComplexType.class);
        assertThat(types.size(), equalTo(1));
        Class c = getClass().getClassLoader().loadClass("uk.gov.hmcts.reform.fpl.model.Solicitor");
        String expected = Resources.toString(Resources.getResource("ccd-definition/ComplexTypes/Solicitor.json"), Charset.defaultCharset());

        JSONAssert.assertEquals(expected, ConfigGenerator.toComplexType(c), false);

    }


    @Test
    public void foo() {
        Function<CaseData, List<Element<HearingBooking>>> foo = CaseData::getHearingDetails;

        builder(CaseData.class, CaseState.class)
                .event(CaseState.Open, CaseState.Submitted)
                .field(x -> x.getCaseName(), DisplayContext.Mandatory)
                .field(x -> x.getC21Order(), DisplayContext.Mandatory)
                .field(x -> x.getHearingDetails(), this::renderSolicitor);
    }

    private void renderSolicitor(HearingBooking solicitor) {
        solicitor.getStartDate();
        solicitor.getType();
    }
}
