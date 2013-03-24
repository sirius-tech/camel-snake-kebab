(ns camel-snake-kebab-test
  (:use clojure.test
        camel-snake-kebab))

(deftest parse-test
  (is (= ["foo" "bar"] (parse "foo bar")))
  (is (= ["foo" "bar"] (parse "foo-bar")))
  (is (= ["foo" "Bar"] (parse "fooBar")))
  (is (= ["Foo" "Bar"] (parse "FooBar")))
  (is (= ["foo" "bar"] (parse "foo_bar")))
  (is (= ["FOO" "BAR"] (parse "FOO_BAR"))))

(def zip (partial map vector))

(deftest format-case-test
  (testing "examples"
    (is (= 'FluxCapacitor  (->CamelCase 'flux-capacitor)))
    (is (= "I_AM_CONSTANT" (->SNAKE_CASE "I am constant")))
    (is (= :object-id      (->kebab-case :object_id))))

  (testing "all the combinations"
    (let
      [inputs    ["FooBar"
                  "fooBar"
                  "FOO_BAR"
                  "foo_bar"
                  "foo-bar"
                  "Foo_Bar"
                  "Foo_bar"]
       functions [->CamelCase
                  ->camelCase
                  ->SNAKE_CASE
                  ->snake_case
                  ->kebab-case
                  ->Camel_Snake_Case
                  ->Camel_snake_case]
       formats   [identity keyword symbol]]

      (dorun
        (for [input inputs, format formats, [output function] (zip inputs functions)]
          (is (= (format output) (function (format input)))))))))
