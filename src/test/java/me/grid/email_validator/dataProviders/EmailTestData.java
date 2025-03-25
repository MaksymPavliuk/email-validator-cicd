package me.grid.email_validator.dataProviders;

import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Stream;

public class EmailTestData {

    public static Stream<Arguments> emailListProvider() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                "valid1@example.com",
                                "δοκιμή@παράδειγμα.ελ",
                                "用户@例子.测试",
                                "test3@sub.domain.com",
                                "gero30+_+_+_+_......--@me.co.co.me",
                                "a@a.ua",
                                "aAAaaaaaaaaaaaaafdsafdadfsdsffdsafadsdfasfdsfdsfdsafdsafdassafdsafdasfdasfdsafdsafadsfadsfads@me.com" // 100
                        ),
                        List.of(
                                ">>>>@gmail.com",
                                "!><>?>{}{@gmail.com",
                                "noatsymbolgmail.com",
                                "@nobody.com",
                                "nodomain@gmail.",
                                "nodot@gmailcom"
                        ))
        );
    }

}