GOJEK Parking lot

A simple parking lot program to allocate vehicles in the lot using nearest first strategy.

### Prerequisite

* JDK 1.8.

* Maven 2.

* Junit 4.12

### Installation

To compile, install dependencies and run tests

```
$sudo bin/setup
```

### Running the program

##### Interactive mode

``` 
$sudo bin/parking_lot
```

###### Supported  commands

```
-create_parking_lot {capacity}
-park {car_number} {car_colour}
-leave {slot}
-status
-registration_numbers_for_cars_with_color {car_colour}
-slot_numbers_for_cars_with_colour {car_colour}
-slot_number_for_registration_number {car_number}
```

##### File mode

``` 
$sudo bin/parking_lot parking_lot/resources/parking_lot_file_inputs.txt
```


