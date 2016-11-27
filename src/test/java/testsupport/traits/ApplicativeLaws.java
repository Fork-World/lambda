package testsupport.traits;

import com.jnape.palatable.lambda.functions.builtin.fn2.Map;
import com.jnape.palatable.lambda.functor.applicative.Applicative;
import com.jnape.palatable.lambda.semigroup.Present;
import com.jnape.palatable.traitor.traits.Trait;

import java.util.Optional;
import java.util.Random;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static java.util.function.Function.identity;

public class ApplicativeLaws<App extends Applicative> implements Trait<Applicative<?, App>> {

    @Override
    public void test(Applicative<?, App> applicative) {
        Iterable<Optional<String>> testResults = Map.<Function<Applicative<?, App>, Optional<String>>, Optional<String>>map(
                f -> f.apply(applicative),
                asList(this::testIdentity, this::testComposition, this::testHomomorphism, this::testInterchange)
        );
        new Present<String>((x, y) -> x + "\n\t - " + y)
                .reduceLeft(testResults)
                .ifPresent(s -> {
                    throw new AssertionError("The following Applicative laws did not hold for instance of " + applicative.getClass() + ": \n\t - " + s);
                });
    }

    private Optional<String> testIdentity(Applicative<?, App> applicative) {
        Applicative<Integer, App> v = applicative.pure(1);
        Applicative<Function<? super Integer, ? extends Integer>, App> pureId = v.pure(identity());
        return v.sequence(pureId).equals(v)
                ? Optional.empty()
                : Optional.of("identity (v.sequence(pureId).equals(v))");
    }

    private Optional<String> testComposition(Applicative<?, App> applicative) {
        Random random = new Random();
        Integer firstInt = random.nextInt(100);
        Integer secondInt = random.nextInt(100);

        Function<? super Function<? super String, ? extends String>, ? extends Function<? super Function<? super String, ? extends String>, ? extends Function<? super String, ? extends String>>> compose = x -> x::compose;
        Applicative<Function<? super String, ? extends String>, App> u = applicative.pure(x -> x + firstInt);
        Applicative<Function<? super String, ? extends String>, App> v = applicative.pure(x -> x + secondInt);
        Applicative<String, App> w = applicative.pure("result: ");

        Applicative<Function<? super Function<? super String, ? extends String>, ? extends Function<? super Function<? super String, ? extends String>, ? extends Function<? super String, ? extends String>>>, App> pureCompose = u.pure(compose);
        return w.sequence(v.sequence(u.sequence(pureCompose))).equals((w.sequence(v)).sequence(u))
                ? Optional.empty()
                : Optional.of("composition (w.sequence(v.sequence(u.sequence(pureCompose))).equals((w.sequence(v)).sequence(u)))");
    }

    private Optional<String> testHomomorphism(Applicative<?, App> applicative) {
        Function<Integer, Integer> f = x -> x + 1;
        int x = 1;

        Applicative<Integer, App> pureX = applicative.pure(x);
        Applicative<Function<? super Integer, ? extends Integer>, App> pureF = applicative.pure(f);
        Applicative<Integer, App> pureFx = applicative.pure(f.apply(x));
        return pureX.sequence(pureF).equals(pureFx)
                ? Optional.empty()
                : Optional.of("homomorphism (pureX.sequence(pureF).equals(pureFx))");
    }

    private Optional<String> testInterchange(Applicative<?, App> applicative) {
        Applicative<Function<? super Integer, ? extends Integer>, App> u = applicative.pure(x -> x + 1);
        int y = 1;

        Applicative<Integer, App> pureY = applicative.pure(y);
        return pureY.sequence(u).equals(u.sequence(applicative.pure(f -> f.apply(y))))
                ? Optional.empty()
                : Optional.of("interchange (pureY.sequence(u).equals(u.sequence(applicative.pure(f -> f.apply(y)))))");
    }
}
