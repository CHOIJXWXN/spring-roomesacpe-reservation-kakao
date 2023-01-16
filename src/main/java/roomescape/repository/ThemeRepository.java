package roomescape.repository;

import roomescape.domain.Theme;
import java.util.Optional;

public interface ThemeRepository {

    Long createTheme(Theme theme);
    Optional<Theme> findThemeById(Long id);
    int updateTheme(Theme theme, Long id);
    int deleteTheme(Long id);


}
