package pl.fastus.wfrpg.hero.domain.race;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.fastus.wfrpg.hero.domain.skill.Skill;
import pl.fastus.wfrpg.hero.domain.talent.Talent;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RaceControllerTest {

    @Mock
    RaceService raceService;

    @InjectMocks
    RaceController controller;

    MockMvc mockMvc;

    List<Race> races;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        races = getRaces();
    }

    @Test
    void getAllRaces() throws Exception {
        given(raceService.getAllRaces()).willReturn(races);

        mockMvc.perform(get("/api/races"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Humans")))
                .andExpect(jsonPath("$[0].stats[0]", is("20")))
                .andExpect(jsonPath("$[1].name", is("Dwarfs")));
    }

    @Test
    void getAllRacesNames() throws Exception {
        List<String> names = List.of("Ludzie", "Krasonludy", "Niziołki");

        given(raceService.getAllRacesNames()).willReturn(names);

        mockMvc.perform(get("/api/races/names"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]", is("Ludzie")))
                .andExpect(jsonPath("$[1]", is("Krasonludy")))
                .andExpect(jsonPath("$[2]", is("Niziołki")));
    }

    private List<Race> getRaces() {
        return List.of(
                Race.builder().id(1L).name("Humans")
                        .skills(List.of(
                                Skill.builder().name("Skill1").build(),
                                Skill.builder().name("Skill2").build(),
                                Skill.builder().name("Skill3").build()))
                        .talents(List.of(
                                Talent.builder().name("Talent1").build(),
                                Talent.builder().name("Talent2").build()
                        )).freeTalents("2")
                        .stats(List.of("20", "30", "25")).build(),
                Race.builder().id(1L).name("Dwarfs")
                        .skills(List.of(
                                Skill.builder().name("Skill1").build(),
                                Skill.builder().name("Skill2").build(),
                                Skill.builder().name("Skill3").build()))
                        .talents(List.of(
                                Talent.builder().name("Talent1").build(),
                                Talent.builder().name("Talent2").build(),
                                Talent.builder().name("Talent3").build()
                        )).freeTalents("4").build()
        );
    }
}
