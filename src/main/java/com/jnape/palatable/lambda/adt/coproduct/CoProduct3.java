package com.jnape.palatable.lambda.adt.coproduct;

import com.jnape.palatable.lambda.functor.Bifunctor;
import com.jnape.palatable.lambda.functor.Functor;

import java.util.Objects;
import java.util.function.Function;

/**
 * A generalization of the coproduct of three types <code>A</code>, <code>B</code>, and <code>C</code>.
 *
 * @param <A> a type parameter representing the first possible type of this coproduct
 * @param <B> a type parameter representing the second possible type of this coproduct
 * @param <C> a type parameter representing the third possible type of this coproduct
 * @see CoProduct2
 */
@FunctionalInterface
public interface CoProduct3<A, B, C> extends Functor<C, CoProduct3<A, B, ?>>, Bifunctor<B, C> {

    /**
     * Type-safe convergence requiring a match against all potential types.
     *
     * @param aFn morphism <code>A -&gt; R</code>
     * @param bFn morphism <code>B -&gt; R</code>
     * @param cFn morphism <code>C -&gt; R</code>
     * @param <R> result type
     * @return the result of applying the appropriate morphism from whichever type is represented by this coproduct to R
     * @see CoProduct2#match(Function, Function)
     */
    <R> R match(Function<? super A, ? extends R> aFn, Function<? super B, ? extends R> bFn,
                Function<? super C, ? extends R> cFn);


    /**
     * Diverge this coproduct by introducing another possible type that it could represent.
     *
     * @param <D> the additional possible type of this coproduct
     * @return a Coproduct4&lt;A, B, C, D&gt;
     * @see CoProduct2#diverge()
     */
    default <D> CoProduct4<A, B, C, D> diverge() {
        return match(CoProduct4::a, CoProduct4::b, CoProduct4::c);
    }

    @Override
    default <D> CoProduct3<A, B, D> fmap(Function<? super C, ? extends D> fn) {
        return biMapR(fn);
    }

    @Override
    @SuppressWarnings("unchecked")
    default <D> CoProduct3<A, D, C> biMapL(Function<? super B, ? extends D> fn) {
        return (CoProduct3<A, D, C>) Bifunctor.super.biMapL(fn);
    }

    @Override
    @SuppressWarnings("unchecked")
    default <D> CoProduct3<A, B, D> biMapR(Function<? super C, ? extends D> fn) {
        return (CoProduct3<A, B, D>) Bifunctor.super.biMapR(fn);
    }

    @Override
    default <D, E> CoProduct3<A, D, E> biMap(Function<? super B, ? extends D> lFn,
                                             Function<? super C, ? extends E> rFn) {
        return match(CoProduct3::a, b -> b(lFn.apply(b)), c -> c(rFn.apply(c)));
    }

    /**
     * Static factory method for wrapping a value of type <code>A</code> in a {@link CoProduct3}.
     *
     * @param a   the value
     * @param <A> a type parameter representing the first possible type of this coproduct
     * @param <B> a type parameter representing the second possible type of this coproduct
     * @param <C> a type parameter representing the third possible type of this coproduct
     * @return the wrapped value as a CoProduct3&lt;A, B, C&gt;
     */
    static <A, B, C> CoProduct3<A, B, C> a(A a) {
        class _A implements CoProduct3<A, B, C> {

            private final A a;

            private _A(A a) {
                this.a = a;
            }

            @Override
            public <R> R match(Function<? super A, ? extends R> aFn, Function<? super B, ? extends R> bFn,
                               Function<? super C, ? extends R> cFn) {
                return aFn.apply(a);
            }

            @Override
            public boolean equals(Object other) {
                return other instanceof _A
                        && Objects.equals(a, ((_A) other).a);
            }

            @Override
            public int hashCode() {
                return Objects.hash(a);
            }

            @Override
            public String toString() {
                return "CoProduct3{" +
                        "a=" + a +
                        '}';
            }
        }

        return new _A(a);
    }

    /**
     * Static factory method for wrapping a value of type <code>A</code> in a {@link CoProduct3}.
     *
     * @param b   the value
     * @param <A> a type parameter representing the first possible type of this coproduct
     * @param <B> a type parameter representing the second possible type of this coproduct
     * @param <C> a type parameter representing the third possible type of this coproduct
     * @return the wrapped value as a CoProduct3&lt;A, B, C&gt;
     */
    static <A, B, C> CoProduct3<A, B, C> b(B b) {
        class _B implements CoProduct3<A, B, C> {

            private final B b;

            private _B(B b) {
                this.b = b;
            }

            @Override
            public <R> R match(Function<? super A, ? extends R> aFn, Function<? super B, ? extends R> bFn,
                               Function<? super C, ? extends R> cFn) {
                return bFn.apply(b);
            }

            @Override
            public boolean equals(Object other) {
                return other instanceof _B
                        && Objects.equals(b, ((_B) other).b);
            }

            @Override
            public int hashCode() {
                return Objects.hash(b);
            }

            @Override
            public String toString() {
                return "CoProduct3{" +
                        "b=" + b +
                        '}';
            }
        }

        return new _B(b);
    }

    /**
     * Static factory method for wrapping a value of type <code>A</code> in a {@link CoProduct3}.
     *
     * @param c   the value
     * @param <A> a type parameter representing the first possible type of this coproduct
     * @param <B> a type parameter representing the second possible type of this coproduct
     * @param <C> a type parameter representing the third possible type of this coproduct
     * @return the wrapped value as a CoProduct3&lt;A, B, C&gt;
     */
    static <A, B, C> CoProduct3<A, B, C> c(C c) {
        class _C implements CoProduct3<A, B, C> {

            private final C c;

            private _C(C c) {
                this.c = c;
            }

            @Override
            public <R> R match(Function<? super A, ? extends R> aFn, Function<? super B, ? extends R> bFn,
                               Function<? super C, ? extends R> cFn) {
                return cFn.apply(c);
            }

            @Override
            public boolean equals(Object other) {
                return other instanceof _C
                        && Objects.equals(c, ((_C) other).c);
            }

            @Override
            public int hashCode() {
                return Objects.hash(c);
            }

            @Override
            public String toString() {
                return "CoProduct3{" +
                        "c=" + c +
                        '}';
            }
        }

        return new _C(c);
    }
}
