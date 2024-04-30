package ru.starkov.shared.common;

import lombok.Getter;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Getter
public class Result<S, F extends Collection<?>> {

    private boolean success;
    S value;
    F errors;

    public static <S, F extends Collection<?>> Result<S, F> success(S value) {
        var result = new Result<S, F>();
        result.success = true;
        result.value = value;
        return result;
    }

    public static <S, F extends Collection<?>> Result<S, F> fail(F errors) {
        var result = new Result<S, F>();
        result.success = false;
        result.errors = errors;
        return result;
    }

    @SuppressWarnings("UnusedReturnValue")
    public Result<S, F> onSuccess(Consumer<S> action) {
        if (this.value != null) {
            action.accept(this.value);
        }
        return this;
    }
    @SuppressWarnings("UnusedReturnValue")
    public Result<S, F> onFail(Consumer<F> action) {
        if (this.errors != null && !this.errors.isEmpty()) {
            action.accept(this.errors);
        }
        return this;
    }
    public <R> Result<R, F> map(Function<S, R> mapper) {
        if (success) {
            return Result.success(mapper.apply(value));
        } else {
            return Result.fail(errors);
        }
    }

    public S orElseGet(Supplier<S> other) {
        return success ? value : other.get();
    }

}
