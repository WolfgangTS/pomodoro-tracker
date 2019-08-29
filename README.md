# pomodoro-tracker

A [reagent](https://github.com/reagent-project/reagent) application designed to model the pomodoro timer technique.
## Screenshot:
![screenshot](https://github.com/emiflake/pomodoro-tracker/blob/feat-docs-screenshot/frame_chrome_mac_dark.png)
## Development Mode

### cljs-devtools

To enable:

1. Open Chrome's DevTools,`Ctrl-Shift-i`
2. Open "Settings", `F1`
3. Check "Enable custom formatters" under the "Console" section
4. close and re-open DevTools

### Compile css:

Compile css file once.

```
lein garden once
```

Automatically recompile css file on change.

```
lein garden auto
```

### Run application:

```
lein clean
lein figwheel dev
```

Figwheel will automatically push cljs changes to the browser.

Wait a bit, then browse to [http://localhost:3449](http://localhost:3449).

## Production Build

```
lein clean
lein cljsbuild once min
```
