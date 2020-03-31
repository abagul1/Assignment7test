Changes to Assignment 5 Model:

    We changed our model to better fit the parameters of the animation builder that
    was provided in the starter code. Instead of having separate classes for different
    operations, the model now only requires one operation which takes all the parameters
    of the given shape, and adjusts each one within the tick time frame.

    The abstract class for an operation was removed since there is only one operation
    that is needed. The verbose output of our model was changed to support the required
    format based on the spec of the assignment.

    The insert element method was changed to support a type of shape and its id, rather
    than passing the shape itself. The model will create a new instance of the shape based
    on the parameters given in the first motion of the shape.

Assignment 6 Key Notes:

    The three views were broken into 3 separate classes, each implementing the IView interface.
    The text and svg views also extend an abstract text view class. Each view class has an
    execute method which runs the appropriate commands to produce the desired view, whether it
    be in text form, svg form, or as a visual animation.

    The view factory class creates the appropriate instance of a view depending on the parameters
    given in the command line. These command line parameters are parsed in the main method,
    and are appropriately handled by the executing view, to ensure that the input parameters are
    valid.

    A timer was used to modulate the speed of the animation and to keep it repeating until the
    user exited the program. Since most error checking is handled in the factory class, the JOption
    pane is only needed once in the visual view in case a model is null.

    The text and svg views are able to output to the console if no output path is specified, and
    will create a new file with the given name if the output file does not already exist.

Operational Notes:

    The way our model is structured, each tick represents a frame, so the speed is both the
    frame rate and the speed of execution. For optimal animation rendering use speeds above 10.
