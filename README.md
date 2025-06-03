mr-clip -- A minimal reproducible of a possible bug in Compose Desktop

The bug: the desktop window appears to be sizing certain elements instead of clipping them.

Versions:
os=Windows 10
jdk=temurin-21.0.6
kotlin.version=1.9.22
compose.version=1.6.0

Demonstration
----

The code creates two boxes inside a third pan and zoom box.

The first box is red, with height of 200.dp, positioned at offset 600.dp.

The second box is blue, with height of 800.dp, position at offset 0.dp.

The desktop window is sized with height of 700.dp.

When the demonstration runs, the system _clips_ the red box. Its height is
set to 200.dp, even tho that means part of it is outside the desktop window. But it _sizes_ the blue box instead of
clipping it, to ~660.dp. Both boxes extend beyond the desktop window's boundary. One of them is clipped, one of them is
sized.

As the window's height is increased by the user, the blue box receives size changes until it finally hits the requested
800.dp.

If you restart the program, then use pan (drag the client area of the window) or zoom (use the scroll wheel in the
client area of the window), you can see this visually. But you can also watch the console to see that the red box keeps
its requested size, but the blue box changes its size.

This effect appears to depend on the fact that the blue box's requested height is greater than the height of the desktop
window, while the red box's requested height is not.

