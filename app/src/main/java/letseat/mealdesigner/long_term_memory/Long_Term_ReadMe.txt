Using the Long_Term_Interface

Any class which needs to access long-term memory can create or extend Long_Term_Interface.java

Within Long_Term_Interface.java, the main functions which can be used to interact with long-term memory are:
    can be reused throughout the lifetime of an activity. (you do not need to create a new Long_Term_Interface object every time you wish to access long-term memory)
    In addition to accessing the recipes from long-term memory, this class also contains functionality for interacting with an index file:
        This index file maps one-to-one between user-supplied recipe names and a program-generated filename
        That said, do not use the user-supplied recipe name as its actual filename in long-term memory. (do not pass user-supplied recipe names as arguments for filenames in the actual functions)
        The reason for this is because user-generated names are likely to have very poor syntax, and may inherently cause errors.
        For this reason, the index file shall map those user-supplied recipe names to program-generated filenames which can be guaranteed to not cause errors.



    public ArrayList<String> getLinesFromFile(String filename)
        This returns the contents of a file, line-by-line, as an ArrayList of type String.  Each line is a member of the return value.
        If a file contains only one line, then the ArrayList will contain only one member.


    public RecipeHead parseLineToRecipe(String input)
        The raw data from a recipe file in long-term memory is passed into this function.
        A wrapper class for a series of doubly-linked lists is returned.
        See the outline for the RecipeHead class below for more info


    public String convertRecipeToWriteable(RecipeHead recipe)
        When it is time to write a recipe back to long-term memory, use this method.
        An entire recipe is passed as an argument,
        A write-to-memory-ready String is returned.
        This String is what actually gets written back to memory.


    public boolean writeRecipeToFile(String filename, String data)
        Pass the return value from "Long_Term_Interface.convertRecipeToWriteable(RecipeHead)" function as the second argument.
        This function will write that data to the specified filename.
        WARNING:  This function will overwrite any data already in the file specified by the filename.


    public String getDAT()
        In case you need to put a timestamp on something, use this method.
        returns the timestamp in the format: ("hour" is in 24-hour military form)
            Month_Day_Year_Hour_Minute_Second_Millisecond
            "10_07_2016_00_19_24_329" would mean "October 7, 2016, at 12:19:24.329 AM"
            24-hour Military time lays out hours as follows:
                00_00 := 12:00 AM
                01_00 := 1:00 AM
                .
                .
                .
                12_00 := 12:00 PM
                13_00 := 1:00 PM
                14_00 := 2:00 PM
                .
                .
                .
                22_00 := 10:00 PM
                23_00 := 11:00 PM
                00_00 := 12:00 AM

                Basically, for any hour in the afternoon, add 12 to get the military time for the hour.
                Minutes stay exactly the same, such that:
                01_45 := 1:45 AM
                13_59 := 1:59 PM
                06_13 := 6:13 AM
                18_01 := 6:01 PM

RecipeHead class
    Holds four doubly-linked lists, each consisting of ListComponent objects
    Also holds the name of the recipe as a member variable of type String
    Also holds sizes for all the lists, by tracking the number of additions and removals from each list


    public String name()
        returns the name of this particular recipe

    public void addEquipment(String equipment_name, int quantity_needed,String additionalText)
        Use this function to add another node to the equipment list for this recipe

    public void addIngredient(String name, double quantity, ListComponent.UnitOfMeasure unit_of_measure, String preparation_procedure)
        Use this function to add another node to the ingredient list for this recipe

    public void addProcedureWithTimer(String name, double timer_in_seconds, String critical_points)
        Use this function to add another node to the Procedure list for this recipe
        This will create the node without a timer value

    public void addProcedureWithoutTimer(String name, String critical_points)
        This function will add another nooe to the Procedure list
        The node thus created will have a timer value

    public void addComment(String text)
        Use this function to add another comment to the Comments list for this recipe


    public int listSize(ComponentType componentType)
        Use this function to get the current size of a particular list
        This function takes in an enum (laid out in ListComponent.ComponentType)
        The return value is the size of the list associated with that ComponentType enum value argument

    public ListComponent getList(ComponentType componentType)
        This function returns the current head node of a particular list
        The list is selected by the argument

    public boolean removeComponent(ComponentType componentType, String text)
        This function will safely remove a single node within a specified list (componentType) which has a matching _text member variable as the argument "text".
        If no node is found with the specified "text" value, then false is returned; otherwise, true is returned if the node has been successfully removed

    public boolean removeComponent(ListComponent node)
        This function will safely remove a node which has already been located inside a list anchored in RecipeHead.
        It will also update any _previous and _next variables such that the list remains unbroken despite the removal.

    public ArrayList<ListComponent> getAllNodes(ComponentType componentType)
        This function returns an ArrayList of type ListComponent, which is all the nodes of a particular list.
        componentType specifies which list is to be returned

    public String getRecipeInfoInStringFormat()
        When the time comes to write a recipe through to long-term memory, this function provides all recipe information -- collected from all nodes of all lists -- in a single String
        This returned String will contain demarkers, which are non-printable ASCII characters, so it is inadvisable to use this as a source for a print-to-screen list of recipe information

    public void resetNodesInASection(ArrayList<ListComponent> listNew)
        Suppose the user wants to rearrange the order of a particular list.  Simply capture that final order in an ArrayList of ListComponents and pass that as an argument in this function.
        The list corresponding to the componentType of the first member of this list will be replaced by the members of the ArrayList passed into this function.
        All _next and _previous values will be updated automatically.
        Use this only if RecipeHead.validateEntireSectionRewrite(ArrayList<ListComponent>) returns true

    public boolean validateWholeSectionRewrite(ArrayList<ListComponent> candidate)
        Used to verify that all members of a list have the same ComponentType value as the first member of this list.
        Will allow ComponentType.PROCEDURE and ComponentType.PROCEDURE_WITH_TIMER in the same list, as they both lay out cooking procedures

    public void printAll()
        A debugging method which prints (to console) all information on the recipe


ListComponent.java uses overloaded constructors to create ListComponent objects with certain values for _componentType


    Equipment constructor:                  public ListComponent(String name, int quantity,String text2)
    Ingredient constructor:                 public ListComponent(String name, double quantity, String preparation_procedure,  UnitOfMeasure unit_of_measure)
    Procedure, with timer, constructor:     public ListComponent(String name, double timer_in_seconds, String hazards_and_critical_control_points)
    Procedure, without timer, constructor:  public ListComponent(String name, String hazards_and_critical_control_points)
    Comment constructor:                    public ListComponent(String name)
        for all of these constructors, the types of arguments determine how the _componentType member variable is set.


    public void addComponentToEnd(ListComponent nodeNew)
        Appends the incoming node to the end of the list specified by the incoming node's _componentType member variable
        This function should not be called ever, (instead use the add[component] function in the RecipeHead class
        In case this functionality is required, it is available, but it should not be used
        This function will not updated the counts in RecipeHead for the relevant list in RecipeHead
        Using this function automatically invalidates the component count in RecipeHead, but this functionality may be required in an emergency
        Better to have and not need it, than it is to need it and not have it.


    public boolean addNewNextComponent(ListComponent nodeNew) {
        Similar to "ListComonent.addComponentToEnd(ListComponent)", this will insert a new node (passed in the arg) after the node which is having this function called.
        Again, do not call this function directly unless it is an emergency, because calling this function will invalidate the size count in RecipeHead


    public boolean hasNext()
        This should be self-explanatory.


    public ListComponent next()
    public ListComponent setNext(final ListComponent next)
    public ListComponent previous()
    public ListComponent setPrevious(final ListComponent previous)
        Primarily used by RecipeHead to safely update lists.


    public ComponentType componentType()
    public void setName(String name)
    public String name()
    public void setAdditionalText(String additionalText)
    public String additionalText()
    public double getQuantity()
    public boolean hasQuantity()
    public UnitOfMeasure unitOfMeasure()
        Functions for accessing all the data stored by a ListComponent object


    public ArrayList<String> getAllInfo()
        Returns all the information stored in a single ListComponent object in an ArrayList
                output[0] := componentType
                output[1] := name or text
                output[2] := quantity or timer value
                output[3] := unitOfMeasure
                output[4] := additionalText
        This function returns invalid values in a particular index of the output if that field is irrelevant to the componentType
            additionalText is not relevant to Comments, so output[4] would be assigned an empty string
            quantity is also not relevant to Comments, so output[2] would be assigned a value of -1.0
        Use of this function must include checks in the caller for invalid data before displaying this info to the user


    public String getInfoForWrite()
        Returns a single string which contains all the information stored in a single ListComponent object
        Warning:  This returned String contains non-printable ASCII characters
            It is inadvisable to use this for prints to the screen or for display-to-user

    public ArrayList<String> getRelevantInfo()
        Unlike "ListComponent.getAllInfo()", which returns invalid values for irrelevant datafields, this only returns non-null values.
        This is


    public void printRelevantInfo()
        Similar to "ListComponent.getRelevantInfo()", this prints all the relevant info (i.e.:  Does not contain invalid values) to console.

    public void printAllInfo()
        A debugging function which prints all information contained in the specific ListComonent object.

Common useages:

Building a recipe:

    Create new instance of RecipeHead, using the user-supplied recipe name for the sole argument in the constructor.
    Call RecipeHead.addEquipment / RecipeHead.addIngredient / RecipeHead.addProcedure / RecipeHead.addComment for each respective attribute, as needed.

Saving a recipe: (without index filing which is still under construction but not much longer, assuming only good filenames are used as recipe names)

    pass a RecipeHead object as an argument to "Long_Term_Interface.convertRecipeToWriteable(RecipeHead)""
    pass a valid filename in arg0, and the return value of "Long_Term_Interface.convertRecipeToWriteable(RecipeHead)" in arg1, in "Long_Term_Interface.writeRecipeToFile(String,String)"
        i.e.:  writeRecipeToFile( testFilename , convertRecipeToWriteable( myRecipe );

Retrieving a recipe: (again, without using the as-of-yet incomplete Index File access methodology)

    RecipeHead myRecipe = parseLineToRecipe( getLinesFromFile(String filename).get(0) );