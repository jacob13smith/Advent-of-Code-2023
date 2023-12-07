use std::fs::File;
use std::io::{self, BufRead};
use std::path::Path;

fn main() {
    if let Ok(lines) = read_lines("./input.txt") {

        let mut line_idx = 0;
        let mut times:Vec<i64> = Vec::new();
        let mut distances:Vec<i64> = Vec::new();

        for line in lines {
            if let Ok(line_str) = line {
                let measures = line_str.split(" ").collect::<Vec<&str>>();
                if line_idx == 0 {
                    for idx in 1..measures.len() {
                        if measures[idx].len() > 0 {
                            times.push(measures[idx].parse::<i64>().unwrap());
                        }
                    }
                } else {
                    for idx in 1..measures.len() {
                        if measures[idx].len() > 0 {
                            distances.push(measures[idx].parse::<i64>().unwrap());
                        }
                    }
                }
            }
            line_idx += 1;
        }

        let mut prod = 1;
        for i in 0..times.len(){
            let distance_to_beat = distances[i];
            let time = times[i];
            let mut ways_to_beat = 0;
            let mut j = time / 2;
            let mut k = time / 2 + if time % 2 != 0 {1} else {0};
            while j * k > distance_to_beat {
                ways_to_beat += 2;
                j -= 1;
                k += 1;
            }
            if time % 2 == 0 {
                ways_to_beat -= 1;
            }
            prod *= ways_to_beat;
        }
        println!("{}", prod);
    }
}

// The output is wrapped in a Result to allow matching on errors
// Returns an Iterator to the Reader of the lines of the file.
fn read_lines<P>(filename: P) -> io::Result<io::Lines<io::BufReader<File>>>
where P: AsRef<Path>, {
    let file = File::open(filename)?;
    Ok(io::BufReader::new(file).lines())
}
