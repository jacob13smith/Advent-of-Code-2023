use std::collections::HashMap;
use std::fs::File;
use std::io::{self, BufRead};
use std::path::Path;
use regex::Regex;

fn main() {

    if let Ok(lines) = read_lines("./input.txt") {
        let mut sum = 0;

        let mut card_counters: Vec<i32> = vec![1; 220];
        let mut i: usize = 0;

        for line in lines {
            if let Ok(line_str) = line {
                let mut wins: usize = 0;
                let separated_numbers = line_str.split(":").collect::<Vec<&str>>()[1];
                let numbers = separated_numbers.split("|").collect::<Vec<&str>>();

                let pattern = Regex::new(r"\b\d+\b").unwrap();
                let mut winning_numbers = HashMap::<&str, bool>::new();

                for mat in pattern.find_iter(numbers[0]){
                    winning_numbers.insert(mat.as_str(), true);
                }

                for mat in pattern.find_iter(numbers[1]){
                    if winning_numbers.contains_key(mat.as_str()) {
                        wins += 1;
                    }
                }

                for win in 0..wins {
                    if i + win + 1 < 220 {
                        card_counters[i + win + 1] += card_counters[i];
                    }
                }
                
            }
            i += 1;
        }
        for cards in card_counters {
            sum += cards;
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