use std::fs::File;
use std::io::{self, BufRead};
use std::path::Path;
use std::collections::HashMap;
use std::cmp;

fn main() {

    let string_map = HashMap::from([
        ("one", 1),
        ("two", 2),
        ("three", 3),
        ("four", 4),
        ("five", 5),
        ("six", 6),
        ("seven", 7),
        ("eight",8),
        ("nine", 9)
    ]);

    // File hosts.txt must exist in the current path
    if let Ok(lines) = read_lines("./input.txt") {
        // Consumes the iterator, returns an (Optional) String
        let mut sum = 0;
        for line in lines {
            if let Ok(calibration_line) = line {
                let mut first = -1;
                let mut last = -1;
                let chars_list: Vec<char> = calibration_line.chars().collect();
                for i in 0..calibration_line.len() {
                    let c = chars_list[i];
                    if c.is_numeric(){
                        let digit = c.to_string().parse::<i32>().unwrap();
                        if first == -1 {
                            first = digit;
                        }
                        last = digit;
                    } else {
                        for j in i + 1..=cmp::min(calibration_line.len(), i+5) {
                            let substring = &calibration_line[i..j];
                            if let Some(&digit) = string_map.get(substring) {
                                if first == -1 {
                                    first = digit;
                                }
                                last = digit;
                                break;
                            }
                        }
                    }
                }
                sum = sum + first * 10 + last;
            }
        }
        println!("{}", sum);
    }
}

// The output is wrapped in a Result to allow matching on errors
// Returns an Iterator to the Reader of the lines of the file.
fn read_lines<P>(filename: P) -> io::Result<io::Lines<io::BufReader<File>>>
where P: AsRef<Path>, {
    let file = File::open(filename)?;
    Ok(io::BufReader::new(file).lines())
}