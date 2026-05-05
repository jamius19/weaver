package dev.jamius.weaver.service;

import dev.jamius.weaver.entity.Setting;
import dev.jamius.weaver.repository.SettingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppSettingsService {

    private final SettingRepository settingRepository;

    @Value("${weaver.app.signup-enabled}")
    private boolean signupEnabled;

    public boolean isSignupEnabled() {
        Optional<Setting> signupSettings = settingRepository.findByKey("signup-enabled");

        return signupSettings
                .map(setting -> setting.getValue().equals("true"))
                .orElseGet(() -> signupEnabled);
    }
}
