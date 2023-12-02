use std::fs::File;
use std::io::{self, BufRead};
use std::cmp;
use std::path::Path;
use regex::Regex;

fn main() {
    let (REDS, GREENS, BLUES) = (12, 13, 14);

    if let Ok(lines) = read_lines("./input.txt") {

        let mut sum = 0;

        for line in lines {
            if let Ok(game_line) = line {
                let initial_parts = game_line.split(":").collect::<Vec<&str>>();
                let game_number = initial_parts[0].split(" ").collect::<Vec<&str>>()[1].parse::<i32>().unwrap();    
                let (mut reds, mut greens, mut blues) = (0, 0, 0);
                
                let pulls = initial_parts[1].split(";").collect::<Vec<&str>>();

                'pulls: for pull in pulls {
                    let colors = pull.split(",").collect::<Vec<&str>>();
                    let re = Regex::new(r"(\d+)\s+(\w+)").unwrap();
                    for color in colors{
                        if let Some(captures) = re.captures(color) {
                            // Extract the number and word from the captures
                            let number = captures[1].parse::<i32>().unwrap();
                            let word = &captures[2];
                            match word {
                                "red" => {
                                    reds = cmp::max(reds, number);
                                },
                                "blue" =>{
                                    blues = cmp::max(blues, number);
                                },
                                "green" =>{
                                    greens = cmp::max(greens, number);
                                },
                                _ => {}
                            }
                        }
                    }
                }

                // Loop accumulator
                sum += reds * blues * greens;
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